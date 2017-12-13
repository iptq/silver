
package silver.definition.flow.env_parser;

// top::IFlowVertex ::= 'lhsInhV' '(' attr::IName ')' 
public final class PaFlowInh_vertex extends silver.definition.flow.env_parser.NIFlowVertex {

	public static final int i__G_3 = 0;
	public static final int i__G_2 = 1;
	public static final int i_attr = 2;
	public static final int i__G_0 = 3;


	public static final Class<?> childTypes[] = {silver.definition.flow.env_parser.TLhsInhVTerm.class,silver.definition.env.env_parser.TLParent_t.class,silver.definition.env.env_parser.NIName.class,silver.definition.env.env_parser.TRParent_t.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_definition_flow_env_parser_aFlowInh_vertex;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.flow.env_parser.NIFlowVertex.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.flow.env_parser.NIFlowVertex.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[4][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_attr] = new common.Lazy[silver.definition.env.env_parser.NIName.num_inh_attrs];

	}

	public PaFlowInh_vertex(final Object c__G_3, final Object c__G_2, final Object c_attr, final Object c__G_0) {
		super();
		this.child__G_3 = c__G_3;
		this.child__G_2 = c__G_2;
		this.child_attr = c_attr;
		this.child__G_0 = c__G_0;

	}

	private Object child__G_3;
	public final silver.definition.flow.env_parser.TLhsInhVTerm getChild__G_3() {
		return (silver.definition.flow.env_parser.TLhsInhVTerm) (child__G_3 = common.Util.demand(child__G_3));
	}

	private Object child__G_2;
	public final silver.definition.env.env_parser.TLParent_t getChild__G_2() {
		return (silver.definition.env.env_parser.TLParent_t) (child__G_2 = common.Util.demand(child__G_2));
	}

	private Object child_attr;
	public final silver.definition.env.env_parser.NIName getChild_attr() {
		return (silver.definition.env.env_parser.NIName) (child_attr = common.Util.demand(child_attr));
	}

	private Object child__G_0;
	public final silver.definition.env.env_parser.TRParent_t getChild__G_0() {
		return (silver.definition.env.env_parser.TRParent_t) (child__G_0 = common.Util.demand(child__G_0));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i__G_3: return getChild__G_3();
			case i__G_2: return getChild__G_2();
			case i_attr: return getChild_attr();
			case i__G_0: return getChild__G_0();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i__G_3: return child__G_3;
			case i__G_2: return child__G_2;
			case i_attr: return child_attr;
			case i__G_0: return child__G_0;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 4;
	}

	@Override
	public common.Lazy getSynthesized(final int index) {
		return synthesizedAttributes[index];
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
	public boolean hasForward() {
		return false;
	}

	@Override
	public common.Node evalForward(final common.DecoratedNode context) {
		throw new common.exceptions.SilverInternalError("Production silver:definition:flow:env_parser:aFlowInh_vertex erroneously claimed to forward");
	}

	@Override
	public common.Lazy getForwardInheritedAttributes(final int index) {
		return forwardInheritedAttributes[index];
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
		return "silver:definition:flow:env_parser:aFlowInh_vertex";
	}

	static void initProductionAttributeDefinitions() {
		// top.flowDeps = [ lhsInhVertex(attr.aname) ]
		silver.definition.flow.env_parser.PaFlowInh_vertex.synthesizedAttributes[silver.definition.flow.env_parser.Init.silver_definition_flow_env_flowDeps__ON__silver_definition_flow_env_parser_IFlowVertex] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.flow.ast.NFlowVertex)new silver.definition.flow.ast.PlhsInhVertex(context.childDecoratedSynthesizedLazy(silver.definition.flow.env_parser.PaFlowInh_vertex.i_attr, silver.definition.env.env_parser.Init.silver_definition_env_env_parser_aname__ON__silver_definition_env_env_parser_IName))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } })); } };

	}

	public static final common.NodeFactory<PaFlowInh_vertex> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PaFlowInh_vertex> {

		@Override
		public PaFlowInh_vertex invoke(final Object[] children, final Object[] annotations) {
			return new PaFlowInh_vertex(children[0], children[1], children[2], children[3]);
		}
	};

}
