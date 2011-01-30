/*
 * Copyright 2010 The Apache Software Foundation
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hbase.coprocessor;

import org.apache.hadoop.hbase.*;

import java.io.IOException;

/**
 * Defines coprocessor hooks for interacting with operations on the
 * {@link org.apache.hadoop.hbase.master.HMaster} process.
 */
public interface MasterObserver extends Coprocessor {

  /**
   * Called before a new table is created by
   * {@link org.apache.hadoop.hbase.master.HMaster}.
   */
  void preCreateTable(MasterCoprocessorEnvironment env,
      HTableDescriptor desc, byte[][] splitKeys) throws IOException;

  /**
   * Called after the initial table regions have been created.
   * @param env the environment to interact with the framework and master
   * @param regions the initial regions created for the table
   * @param sync whether the client call is waiting for region assignment to
   * complete before returning
   * @throws IOException
   */
  void postCreateTable(MasterCoprocessorEnvironment env,
      HRegionInfo[] regions, boolean sync) throws IOException;

  /**
   * Called before {@link org.apache.hadoop.hbase.master.HMaster} deletes a
   * table
   */
  void preDeleteTable(MasterCoprocessorEnvironment env, byte[] tableName)
      throws IOException;

  /**
   * Called after the table has been deleted, before returning to the client.
   */
  void postDeleteTable(MasterCoprocessorEnvironment env, byte[] tableName)
      throws IOException;

  /**
   * Called prior to modifying a table's properties.
   */
  void preModifyTable(MasterCoprocessorEnvironment env, final byte[] tableName,
      HTableDescriptor htd) throws IOException;

  /**
   * Called after {@link org.apache.hadoop.hbase.master.HMaster} has modified
   * the table's properties in all the table regions.
   */
  void postModifyTable(MasterCoprocessorEnvironment env, final byte[] tableName,
      HTableDescriptor htd) throws IOException;

  /**
   * Called prior to adding a new column family to the table.
   */
  void preAddColumn(MasterCoprocessorEnvironment env, byte[] tableName,
      HColumnDescriptor column) throws IOException;

  /**
   * Called after the new column family has been created.
   */
  void postAddColumn(MasterCoprocessorEnvironment env, byte[] tableName,
      HColumnDescriptor column) throws IOException;

  /**
   * Called prior to modifying a column family's attributes.
   */
  void preModifyColumn(MasterCoprocessorEnvironment env,
      byte [] tableName, HColumnDescriptor descriptor) throws IOException;

  /**
   * Called after the column family has been updated.
   */
  void postModifyColumn(MasterCoprocessorEnvironment env, byte[] tableName,
      HColumnDescriptor descriptor) throws IOException;

  /**
   * Called prior to deleting the entire column family.
   */
  void preDeleteColumn(MasterCoprocessorEnvironment env,
      final byte [] tableName, final byte[] c) throws IOException;

  /**
   * Called after the column family has been deleted.
   */
  void postDeleteColumn(MasterCoprocessorEnvironment env,
      final byte [] tableName, final byte[] c) throws IOException;

  /**
   * Called prior to enabling a table.
   */
  void preEnableTable(MasterCoprocessorEnvironment env, final byte[] tableName)
      throws IOException;

  /**
   * Called after the table has been enabled.
   */
  void postEnableTable(MasterCoprocessorEnvironment env, final byte[] tableName)
      throws IOException;

  /**
   * Called prior to disabling a table.
   */
  void preDisableTable(MasterCoprocessorEnvironment env, final byte[] tableName)
      throws IOException;

  /**
   * Called after the table has been disabled.
   */
  void postDisableTable(MasterCoprocessorEnvironment env, final byte[] tableName)
      throws IOException;

  /**
   * Called prior to moving a given region from one region server to another.
   */
  void preMove(MasterCoprocessorEnvironment env, final HRegionInfo region,
      final HServerInfo srcServer, final HServerInfo destServer)
    throws UnknownRegionException;

  /**
   * Called after the region move has been requested.
   */
  void postMove(MasterCoprocessorEnvironment env, final HRegionInfo region,
      final HServerInfo srcServer, final HServerInfo destServer)
    throws UnknownRegionException;

  /**
   * Called prior to assigning a specific region.
   */
  void preAssign(MasterCoprocessorEnvironment env, final byte [] regionName,
      final boolean force) throws IOException;

  /**
   * Called after the region assignment has been requested.
   */
  void postAssign(MasterCoprocessorEnvironment env, final HRegionInfo regionInfo)
      throws IOException;

  /**
   * Called prior to unassigning a given region.
   */
  void preUnassign(MasterCoprocessorEnvironment env, final byte [] regionName,
      final boolean force) throws IOException;

  /**
   * Called after the region unassignment has been requested.
   */
  void postUnassign(MasterCoprocessorEnvironment env,
      final HRegionInfo regionInfo, final boolean force) throws IOException;

  /**
   * Called prior to requesting rebalancing of the cluster regions, though after
   * the initial checks for regions in transition and the balance switch flag.
   */
  void preBalance(MasterCoprocessorEnvironment env) throws IOException;

  /**
   * Called after the balancing plan has been submitted.
   */
  void postBalance(MasterCoprocessorEnvironment env) throws IOException;

  /**
   * Called prior to modifying the flag used to enable/disable region balancing.
   * @param env the coprocessor instance's environment
   * @param newValue the new flag value submitted in the call
   */
  boolean preBalanceSwitch(MasterCoprocessorEnvironment env,
      final boolean newValue) throws IOException;

  /**
   * Called after the flag to enable/disable balancing has changed.
   * @param env the coprocessor instance's environment
   * @param oldValue the previously set balanceSwitch value
   * @param newValue the newly set balanceSwitch value
   */
  void postBalanceSwitch(MasterCoprocessorEnvironment env,
      final boolean oldValue, final boolean newValue) throws IOException;

  /**
   * Called prior to shutting down the full HBase cluster, including this
   * {@link org.apache.hadoop.hbase.master.HMaster} process.
   */
  void preShutdown(MasterCoprocessorEnvironment env) throws IOException;


  /**
   * Called immediatly prior to stopping this
   * {@link org.apache.hadoop.hbase.master.HMaster} process.
   */
  void preStopMaster(MasterCoprocessorEnvironment env) throws IOException;
}
