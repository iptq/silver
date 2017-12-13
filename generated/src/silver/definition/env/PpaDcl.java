
package silver.definition.env;

// top::DclInfo ::= sg::String sl::Location ns::NamedSignature dcls::[Def] 
public final class PpaDcl extends silver.definition.env.NDclInfo {

	public static final int i_sg = 0;
	public static final int i_sl = 1;
	public static final int i_ns = 2;
	public static final int i_dcls = 3;


	public static final Class<?> childTypes[] = {common.StringCatter.class,core.NLocation.class,silver.definition.env.NNamedSignature.class,common.DecoratedNode.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_definition_env_paDcl;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.env.NDclInfo.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.env.NDclInfo.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[4][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_sl] = new common.Lazy[core.NLocation.num_inh_attrs];
	childInheritedAttributes[i_ns] = new common.Lazy[silver.definition.env.NNamedSignature.num_inh_attrs];

	}

	public PpaDcl(final Object c_sg, final Object c_sl, final Object c_ns, final Object c_dcls) {
		super();
		this.child_sg = c_sg;
		this.child_sl = c_sl;
		this.child_ns = c_ns;
		this.child_dcls = c_dcls;

	}

	private Object child_sg;
	public final common.StringCatter getChild_sg() {
		return (common.StringCatter) (child_sg = common.Util.demand(child_sg));
	}

	private Object child_sl;
	public final core.NLocation getChild_sl() {
		return (core.NLocation) (child_sl = common.Util.demand(child_sl));
	}

	private Object child_ns;
	public final silver.definition.env.NNamedSignature getChild_ns() {
		return (silver.definition.env.NNamedSignature) (child_ns = common.Util.demand(child_ns));
	}

	private Object child_dcls;
	public final common.ConsCell getChild_dcls() {
		return (common.ConsCell) (child_dcls = common.Util.demand(child_dcls));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_sg: return getChild_sg();
			case i_sl: return getChild_sl();
			case i_ns: return getChild_ns();
			case i_dcls: return getChild_dcls();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_sg: return child_sg;
			case i_sl: return child_sl;
			case i_ns: return child_ns;
			case i_dcls: return child_dcls;

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
		throw new common.exceptions.SilverInternalError("Production silver:definition:env:paDcl erroneously claimed to forward");
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
		return "silver:definition:env:paDcl";
	}

	static void initProductionAttributeDefinitions() {
		// top.sourceGrammar = sg
		silver.definition.env.PpaDcl.synthesizedAttributes[silver.definition.env.Init.silver_definition_env_sourceGrammar__ON__silver_definition_env_DclInfo] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.StringCatter)context.childAsIs(silver.definition.env.PpaDcl.i_sg)); } };
		// top.sourceLocation = sl
		silver.definition.env.PpaDcl.synthesizedAttributes[silver.definition.env.Init.silver_definition_env_sourceLocation__ON__silver_definition_env_DclInfo] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return context.childDecorated(silver.definition.env.PpaDcl.i_sl).undecorate(); } };
		// top.fullName = ns.fullName
		silver.definition.env.PpaDcl.synthesizedAttributes[silver.definition.env.Init.silver_definition_env_fullName__ON__silver_definition_env_DclInfo] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.StringCatter)context.childDecorated(silver.definition.env.PpaDcl.i_ns).synthesized(silver.definition.env.Init.silver_definition_env_fullName__ON__silver_definition_env_NamedSignature)); } };
		// boundvars = top.typerep.freeVariables
		silver.definition.env.PpaDcl.localAttributes[silver.definition.env.Init.boundvars__ON__silver_definition_env_paDcl] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)((silver.definition.type.NType)context.synthesized(silver.definition.env.Init.silver_definition_env_typerep__ON__silver_definition_env_DclInfo)).decorate(context, (common.Lazy[])null).synthesized(silver.definition.type.Init.silver_definition_type_freeVariables__ON__silver_definition_type_Type)); } };
		// ns.boundVariables = top.boundVariables ++ boundvars
		silver.definition.env.PpaDcl.childInheritedAttributes[silver.definition.env.PpaDcl.i_ns][silver.definition.env.Init.silver_definition_type_boundVariables__ON__silver_definition_env_NamedSignature] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pappend.invoke(context.contextInheritedLazy(silver.definition.env.Init.silver_definition_type_boundVariables__ON__silver_definition_env_DclInfo), context.localAsIsLazy(silver.definition.env.Init.boundvars__ON__silver_definition_env_paDcl))); } };
		// top.unparse = "p@(" ++ sl.unparse ++ ", " ++ unparseTyVars(boundvars, ns.boundVariables) ++ ", " ++ ns.unparse ++ ", " ++ unparseDefs(dcls, boundvars) ++ ")"
		silver.definition.env.PpaDcl.synthesizedAttributes[silver.definition.env.Init.silver_definition_env_unparse__ON__silver_definition_env_DclInfo] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return new common.StringCatter((common.StringCatter)(new common.StringCatter("p@(")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)context.childDecorated(silver.definition.env.PpaDcl.i_sl).synthesized(silver.definition.env.Init.silver_definition_env_unparse__ON__core_Location)), (common.StringCatter)new common.StringCatter((common.StringCatter)(new common.StringCatter(", ")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)silver.definition.env.PunparseTyVars.invoke(context.localAsIsLazy(silver.definition.env.Init.boundvars__ON__silver_definition_env_paDcl), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)context.childDecorated(silver.definition.env.PpaDcl.i_ns).inherited(silver.definition.env.Init.silver_definition_type_boundVariables__ON__silver_definition_env_NamedSignature)); } })), (common.StringCatter)new common.StringCatter((common.StringCatter)(new common.StringCatter(", ")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)context.childDecorated(silver.definition.env.PpaDcl.i_ns).synthesized(silver.definition.env.Init.silver_definition_env_unparse__ON__silver_definition_env_NamedSignature)), (common.StringCatter)new common.StringCatter((common.StringCatter)(new common.StringCatter(", ")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)silver.definition.env.PunparseDefs.invoke(context.childAsIsLazy(silver.definition.env.PpaDcl.i_dcls), context.localAsIsLazy(silver.definition.env.Init.boundvars__ON__silver_definition_env_paDcl))), (common.StringCatter)(new common.StringCatter(")")))))))))); } };
		// top.prodDefs = dcls
		silver.definition.env.PpaDcl.synthesizedAttributes[silver.definition.env.Init.silver_definition_env_prodDefs__ON__silver_definition_env_DclInfo] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)context.childAsIs(silver.definition.env.PpaDcl.i_dcls)); } };
		// top.typerep = ns.typerep
		silver.definition.env.PpaDcl.synthesizedAttributes[silver.definition.env.Init.silver_definition_env_typerep__ON__silver_definition_env_DclInfo] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((silver.definition.type.NType)context.childDecorated(silver.definition.env.PpaDcl.i_ns).synthesized(silver.definition.env.Init.silver_definition_env_typerep__ON__silver_definition_env_NamedSignature)); } };

	}

	public static final common.NodeFactory<PpaDcl> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PpaDcl> {

		@Override
		public PpaDcl invoke(final Object[] children, final Object[] annotations) {
			return new PpaDcl(children[0], children[1], children[2], children[3]);
		}
	};

}
