
package silver.definition.core;

// top::ProductionStmt ::= dl::DefLHS '.' attr::QNameAttrOccur '=' e::Expr ';' 
public final class PattributeDef extends silver.definition.core.NProductionStmt {

	public static final int i_dl = 0;
	public static final int i__G_4 = 1;
	public static final int i_attr = 2;
	public static final int i__G_2 = 3;
	public static final int i_e = 4;
	public static final int i__G_0 = 5;


	public static final Class<?> childTypes[] = {silver.definition.core.NDefLHS.class,silver.definition.core.TDot_t.class,silver.definition.core.NQNameAttrOccur.class,silver.definition.core.TEqual_t.class,silver.definition.core.NExpr.class,silver.definition.core.TSemi_t.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_definition_core_attributeDef;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.core.NProductionStmt.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.core.NProductionStmt.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[6][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_dl] = new common.Lazy[silver.definition.core.NDefLHS.num_inh_attrs];
	childInheritedAttributes[i_attr] = new common.Lazy[silver.definition.core.NQNameAttrOccur.num_inh_attrs];
	childInheritedAttributes[i_e] = new common.Lazy[silver.definition.core.NExpr.num_inh_attrs];

	}

	public PattributeDef(final Object c_dl, final Object c__G_4, final Object c_attr, final Object c__G_2, final Object c_e, final Object c__G_0, final Object a_core_location) {
		super(a_core_location);
		this.child_dl = c_dl;
		this.child__G_4 = c__G_4;
		this.child_attr = c_attr;
		this.child__G_2 = c__G_2;
		this.child_e = c_e;
		this.child__G_0 = c__G_0;

	}

	private Object child_dl;
	public final silver.definition.core.NDefLHS getChild_dl() {
		return (silver.definition.core.NDefLHS) (child_dl = common.Util.demand(child_dl));
	}

	private Object child__G_4;
	public final silver.definition.core.TDot_t getChild__G_4() {
		return (silver.definition.core.TDot_t) (child__G_4 = common.Util.demand(child__G_4));
	}

	private Object child_attr;
	public final silver.definition.core.NQNameAttrOccur getChild_attr() {
		return (silver.definition.core.NQNameAttrOccur) (child_attr = common.Util.demand(child_attr));
	}

	private Object child__G_2;
	public final silver.definition.core.TEqual_t getChild__G_2() {
		return (silver.definition.core.TEqual_t) (child__G_2 = common.Util.demand(child__G_2));
	}

	private Object child_e;
	public final silver.definition.core.NExpr getChild_e() {
		return (silver.definition.core.NExpr) (child_e = common.Util.demand(child_e));
	}

	private Object child__G_0;
	public final silver.definition.core.TSemi_t getChild__G_0() {
		return (silver.definition.core.TSemi_t) (child__G_0 = common.Util.demand(child__G_0));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_dl: return getChild_dl();
			case i__G_4: return getChild__G_4();
			case i_attr: return getChild_attr();
			case i__G_2: return getChild__G_2();
			case i_e: return getChild_e();
			case i__G_0: return getChild__G_0();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_dl: return child_dl;
			case i__G_4: return child__G_4;
			case i_attr: return child_attr;
			case i__G_2: return child__G_2;
			case i_e: return child_e;
			case i__G_0: return child__G_0;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 6;
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
		return true;
	}

	@Override
	public common.Node evalForward(final common.DecoratedNode context) {
		return ((!((Boolean)core.Pnull.invoke(context.localAsIsLazy(silver.definition.core.Init.problems__ON__silver_definition_core_attributeDef)))) ? ((silver.definition.core.NProductionStmt)new silver.definition.core.PerrorAttributeDef(context.localAsIsLazy(silver.definition.core.Init.problems__ON__silver_definition_core_attributeDef), context.childDecoratedLazy(silver.definition.core.PattributeDef.i_dl), context.childDecoratedLazy(silver.definition.core.PattributeDef.i_attr), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.core.PattributeDef.i_e)), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NProductionStmt)context.undecorate()).getAnno_core_location()); } })) : ((silver.definition.core.NProductionStmt)((common.NodeFactory<silver.definition.core.NProductionStmt>)((silver.definition.env.NDclInfo)context.childDecorated(silver.definition.core.PattributeDef.i_attr).synthesized(silver.definition.core.Init.silver_definition_core_attrDcl__ON__silver_definition_core_QNameAttrOccur)).decorate(context, (common.Lazy[])null).synthesized(silver.definition.core.Init.silver_definition_core_attrDefDispatcher__ON__silver_definition_env_DclInfo)).invoke(new Object[]{context.childDecoratedLazy(silver.definition.core.PattributeDef.i_dl), context.childDecoratedLazy(silver.definition.core.PattributeDef.i_attr), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.core.PattributeDef.i_e)), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NProductionStmt)context.undecorate()).getAnno_core_location()); } }}, null)));
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
		return "silver:definition:core:attributeDef";
	}

	static void initProductionAttributeDefinitions() {
		// top.pp = "\t" ++ dl.pp ++ "." ++ attr.pp ++ " = " ++ e.pp ++ ";"
		silver.definition.core.PattributeDef.synthesizedAttributes[silver.definition.core.Init.silver_definition_env_pp__ON__silver_definition_core_ProductionStmt] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return new common.StringCatter((common.StringCatter)(new common.StringCatter("\t")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)context.childDecorated(silver.definition.core.PattributeDef.i_dl).synthesized(silver.definition.core.Init.silver_definition_env_pp__ON__silver_definition_core_DefLHS)), (common.StringCatter)new common.StringCatter((common.StringCatter)(new common.StringCatter(".")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)context.childDecorated(silver.definition.core.PattributeDef.i_attr).synthesized(silver.definition.core.Init.silver_definition_env_pp__ON__silver_definition_core_QNameAttrOccur)), (common.StringCatter)new common.StringCatter((common.StringCatter)(new common.StringCatter(" = ")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)context.childDecorated(silver.definition.core.PattributeDef.i_e).synthesized(silver.definition.core.Init.silver_definition_env_pp__ON__silver_definition_core_Expr)), (common.StringCatter)(new common.StringCatter(";")))))))); } };
		// top.productionAttributes = []
		silver.definition.core.PattributeDef.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_productionAttributes__ON__silver_definition_core_ProductionStmt] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } };
		// top.defs = []
		silver.definition.core.PattributeDef.synthesizedAttributes[silver.definition.core.Init.silver_definition_env_defs__ON__silver_definition_core_ProductionStmt] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } };
		// dl.defLHSattr = attr
		silver.definition.core.PattributeDef.childInheritedAttributes[silver.definition.core.PattributeDef.i_dl][silver.definition.core.Init.silver_definition_core_defLHSattr__ON__silver_definition_core_DefLHS] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return context.childDecorated(silver.definition.core.PattributeDef.i_attr); } };
		// attr.attrFor = dl.typerep
		silver.definition.core.PattributeDef.childInheritedAttributes[silver.definition.core.PattributeDef.i_attr][silver.definition.core.Init.silver_definition_core_attrFor__ON__silver_definition_core_QNameAttrOccur] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((silver.definition.type.NType)context.childDecorated(silver.definition.core.PattributeDef.i_dl).synthesized(silver.definition.core.Init.silver_definition_env_typerep__ON__silver_definition_core_DefLHS)); } };
		// problems = if null(attr.errors) && attr.attrDcl.isAnnotation then [ err(attr.location, attr.pp ++ " is an annotation, which are supplied to productions as arguments, not defined as equations.") ] else dl.errors ++ attr.errors
		silver.definition.core.PattributeDef.localAttributes[silver.definition.core.Init.problems__ON__silver_definition_core_attributeDef] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((((Boolean)core.Pnull.invoke(context.childDecoratedSynthesizedLazy(silver.definition.core.PattributeDef.i_attr, silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_QNameAttrOccur))) && ((Boolean)((silver.definition.env.NDclInfo)context.childDecorated(silver.definition.core.PattributeDef.i_attr).synthesized(silver.definition.core.Init.silver_definition_core_attrDcl__ON__silver_definition_core_QNameAttrOccur)).decorate(context, (common.Lazy[])null).synthesized(silver.definition.env.Init.silver_definition_env_isAnnotation__ON__silver_definition_env_DclInfo))) ? ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NMessage)new silver.definition.core.Perr(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NQNameAttrOccur)context.childDecorated(silver.definition.core.PattributeDef.i_attr).undecorate()).getAnno_core_location()); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return new common.StringCatter((common.StringCatter)((common.StringCatter)context.childDecorated(silver.definition.core.PattributeDef.i_attr).synthesized(silver.definition.core.Init.silver_definition_env_pp__ON__silver_definition_core_QNameAttrOccur)), (common.StringCatter)(new common.StringCatter(" is an annotation, which are supplied to productions as arguments, not defined as equations."))); } })); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } })) : ((common.ConsCell)core.Pappend.invoke(context.childDecoratedSynthesizedLazy(silver.definition.core.PattributeDef.i_dl, silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_DefLHS), context.childDecoratedSynthesizedLazy(silver.definition.core.PattributeDef.i_attr, silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_QNameAttrOccur)))); } };

	}

	public static final common.NodeFactory<PattributeDef> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PattributeDef> {

		@Override
		public PattributeDef invoke(final Object[] children, final Object[] annotations) {
			return new PattributeDef(children[0], children[1], children[2], children[3], children[4], children[5], annotations[0]);
		}
	};

}
