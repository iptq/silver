
package silver.definition.flow.driver;

public final class PaddAutocopyEqs extends common.FunctionNode {

	public static final int i_prod = 0;
	public static final int i_sigName = 1;
	public static final int i_inhs = 2;
	public static final int i_flowEnv = 3;
	public static final int i_realEnv = 4;


	public static final Class<?> childTypes[] = { common.StringCatter.class,silver.definition.env.NNamedSignatureElement.class,common.DecoratedNode.class,common.DecoratedNode.class,common.DecoratedNode.class };

	public static final int num_local_attrs = Init.count_local__ON__silver_definition_flow_driver_addAutocopyEqs;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[5][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static{
	childInheritedAttributes[i_sigName] = new common.Lazy[silver.definition.env.NNamedSignatureElement.num_inh_attrs];

	}

	public PaddAutocopyEqs(final Object c_prod, final Object c_sigName, final Object c_inhs, final Object c_flowEnv, final Object c_realEnv) {
		this.child_prod = c_prod;
		this.child_sigName = c_sigName;
		this.child_inhs = c_inhs;
		this.child_flowEnv = c_flowEnv;
		this.child_realEnv = c_realEnv;

	}

	private Object child_prod;
	public final common.StringCatter getChild_prod() {
		return (common.StringCatter) (child_prod = common.Util.demand(child_prod));
	}

	private Object child_sigName;
	public final silver.definition.env.NNamedSignatureElement getChild_sigName() {
		return (silver.definition.env.NNamedSignatureElement) (child_sigName = common.Util.demand(child_sigName));
	}

	private Object child_inhs;
	public final common.ConsCell getChild_inhs() {
		return (common.ConsCell) (child_inhs = common.Util.demand(child_inhs));
	}

	private Object child_flowEnv;
	public final common.DecoratedNode getChild_flowEnv() {
		return (common.DecoratedNode) (child_flowEnv = common.Util.demand(child_flowEnv));
	}

	private Object child_realEnv;
	public final common.DecoratedNode getChild_realEnv() {
		return (common.DecoratedNode) (child_realEnv = common.Util.demand(child_realEnv));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_prod: return getChild_prod();
			case i_sigName: return getChild_sigName();
			case i_inhs: return getChild_inhs();
			case i_flowEnv: return getChild_flowEnv();
			case i_realEnv: return getChild_realEnv();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_prod: return child_prod;
			case i_sigName: return child_sigName;
			case i_inhs: return child_inhs;
			case i_flowEnv: return child_flowEnv;
			case i_realEnv: return child_realEnv;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 5;
	}

	@Override
	public common.Lazy[] getLocalInheritedAttributes(final int key) {
		return localInheritedAttributes[key];
	}

	@Override
	public common.Lazy[] getChildInheritedAttributes(final int key) {
		return childInheritedAttributes[key];
	}

	@Override
	public common.Lazy getLocal(final int key) {
		return localAttributes[key];
	}

	@Override
	public final int getNumberOfLocalAttrs() {
		return num_local_attrs;
	}

	@Override
	public final String getNameOfLocalAttr(final int index) {
		return occurs_local[index];
	}

	@Override
	public String getName() {
		return "silver:definition:flow:driver:addAutocopyEqs";
	}

	public static common.ConsCell invoke(final Object c_prod, final Object c_sigName, final Object c_inhs, final Object c_flowEnv, final Object c_realEnv) {
		try {
		final common.DecoratedNode context = new PaddAutocopyEqs(c_prod, c_sigName, c_inhs, c_flowEnv, c_realEnv).decorate();
		//if null(inhs) then [] else (if null(lookupInh(prod, sigName.elementName, head(inhs), flowEnv)) && ! null(getOccursDcl(head(inhs), sigName.typerep.typeName, realEnv)) then [ pair(rhsVertex(sigName.elementName, head(inhs)), lhsInhVertex(head(inhs))) ] else []) ++ addAutocopyEqs(prod, sigName, tail(inhs), flowEnv, realEnv)
		return (common.ConsCell)((((Boolean)core.Pnull.invoke(context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_inhs))) ? ((common.ConsCell)core.Pnil.invoke()) : ((common.ConsCell)core.Pappend.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((((Boolean)core.Pnull.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)silver.definition.flow.env.PlookupInh.invoke(context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_prod), context.childDecoratedSynthesizedLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_sigName, silver.definition.env.Init.silver_definition_env_elementName__ON__silver_definition_env_NamedSignatureElement), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)core.Phead.invoke(context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_inhs))); } }, context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_flowEnv))); } })) && (!((Boolean)core.Pnull.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)silver.definition.env.PgetOccursDcl.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)core.Phead.invoke(context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_inhs))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)((silver.definition.type.NType)context.childDecorated(silver.definition.flow.driver.PaddAutocopyEqs.i_sigName).synthesized(silver.definition.env.Init.silver_definition_env_typerep__ON__silver_definition_env_NamedSignatureElement)).decorate(context, (common.Lazy[])null).synthesized(silver.definition.env.Init.silver_definition_env_typeName__ON__silver_definition_type_Type)); } }, context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_realEnv))); } })))) ? ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NPair)new core.Ppair(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.flow.ast.NFlowVertex)new silver.definition.flow.ast.PrhsVertex(context.childDecoratedSynthesizedLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_sigName, silver.definition.env.Init.silver_definition_env_elementName__ON__silver_definition_env_NamedSignatureElement), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)core.Phead.invoke(context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_inhs))); } })); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.flow.ast.NFlowVertex)new silver.definition.flow.ast.PlhsInhVertex(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)core.Phead.invoke(context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_inhs))); } })); } })); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } })) : ((common.ConsCell)core.Pnil.invoke())); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)silver.definition.flow.driver.PaddAutocopyEqs.invoke(context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_prod), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_sigName)), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Ptail.invoke(context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_inhs))); } }, context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_flowEnv), context.childAsIsLazy(silver.definition.flow.driver.PaddAutocopyEqs.i_realEnv))); } }))));

		} catch(Throwable t) {
			throw new common.exceptions.TraceException("Error while evaluating function silver:definition:flow:driver:addAutocopyEqs", t);
		}
	}

	public static final common.NodeFactory<common.ConsCell> factory = new Factory();

	public static final class Factory extends common.NodeFactory<common.ConsCell> {
		@Override
		public common.ConsCell invoke(final Object[] children, final Object[] namedNotApplicable) {
			return PaddAutocopyEqs.invoke(children[0], children[1], children[2], children[3], children[4]);
		}
	};
}