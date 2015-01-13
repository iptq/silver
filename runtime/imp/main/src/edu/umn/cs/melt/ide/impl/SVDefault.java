package edu.umn.cs.melt.ide.impl;

import ide.NIdeEnv;

import common.ConsCell;
import common.Node;

import core.NIOVal;
import core.Pioval;

/**
 * This class exists to provide default behaviors for all methods of the interface.
 * 
 * The implementor class should only need to provide those methods it actually uses. The others
 * should either be never accessed (thus throwing exceptions below) or have completely valid
 * behavior if absent (seen below).
 */
public abstract class SVDefault implements SVInterface {

	// Required methods:
	@Override
	public abstract String name();
	@Override
	public abstract String markerErrorName();
	
	@Override
	public NIOVal build(ConsCell properties, NIdeEnv env, Object iotoken) {
		// Introducing the bit to plugin.xml that results in the code being run
		// that eventually calls this is a result of a 'builder' function being given.
		// It should never be the case that this is unimplemented and then called.
		throw new UnsupportedOperationException("Build should only be called if supplied by the plugin in the silver ide declaration.");
	}

	@Override
	public NIOVal postbuild(ConsCell properties, NIdeEnv env, Object iotoken) {
		// An entirely valid course of action is to do nothing. Do so by default.
		// Seamlessly handles a 'builder' given but not a 'postbuilder'.
		return new Pioval(iotoken, ConsCell.nil);
	}

	@Override
	public NIOVal export(ConsCell properties, NIdeEnv env, Object iotoken) {
		// Introducing the bit to plugin.xml that results in the code being run
		// that eventually calls this is a result of a 'exporter' function being given.
		// It should never be the case that this is unimplemented and then called.
		throw new UnsupportedOperationException("Export should only be called if supplied by the plugin in the silver ide declaration.");
	}

	@Override
	public ConsCell getFolds(Node root) {
		// Introducing the bit to plugin.xml that results in the code being run
		// that eventually calls this is a result of a 'folder' function being given.
		// It should never be the case that this is unimplemented and then called.
		throw new UnsupportedOperationException("GetFolds should only be called if supplied by the plugin in the silver ide declaration.");
	}

}