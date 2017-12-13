
package silver.definition.core;

// top::ProductionStmt ::= 'production' 'attribute' a::Name '::' te::TypeExpr ';' 
public final class PproductionAttributeDcl extends silver.definition.core.NProductionStmt {

	public static final int i__G_5 = 0;
	public static final int i__G_4 = 1;
	public static final int i_a = 2;
	public static final int i__G_2 = 3;
	public static final int i_te = 4;
	public static final int i__G_0 = 5;


	public static final Class<?> childTypes[] = {silver.definition.core.TProduction_kwd.class,silver.definition.core.TAttribute_kwd.class,silver.definition.core.NName.class,silver.definition.core.TColonColon_t.class,silver.definition.type.syntax.NTypeExpr.class,silver.definition.core.TSemi_t.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_definition_core_productionAttributeDcl;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.core.NProductionStmt.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.core.NProductionStmt.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[6][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_a] = new common.Lazy[silver.definition.core.NName.num_inh_attrs];
	childInheritedAttributes[i_te] = new common.Lazy[silver.definition.type.syntax.NTypeExpr.num_inh_attrs];

	}

	public PproductionAttributeDcl(final Object c__G_5, final Object c__G_4, final Object c_a, final Object c__G_2, final Object c_te, final Object c__G_0, final Object a_core_location) {
		super(a_core_location);
		this.child__G_5 = c__G_5;
		this.child__G_4 = c__G_4;
		this.child_a = c_a;
		this.child__G_2 = c__G_2;
		this.child_te = c_te;
		this.child__G_0 = c__G_0;

	}

	private Object child__G_5;
	public final silver.definition.core.TProduction_kwd getChild__G_5() {
		return (silver.definition.core.TProduction_kwd) (child__G_5 = common.Util.demand(child__G_5));
	}

	private Object child__G_4;
	public final silver.definition.core.TAttribute_kwd getChild__G_4() {
		return (silver.definition.core.TAttribute_kwd) (child__G_4 = common.Util.demand(child__G_4));
	}

	private Object child_a;
	public final silver.definition.core.NName getChild_a() {
		return (silver.definition.core.NName) (child_a = common.Util.demand(child_a));
	}

	private Object child__G_2;
	public final silver.definition.core.TColonColon_t getChild__G_2() {
		return (silver.definition.core.TColonColon_t) (child__G_2 = common.Util.demand(child__G_2));
	}

	private Object child_te;
	public final silver.definition.type.syntax.NTypeExpr getChild_te() {
		return (silver.definition.type.syntax.NTypeExpr) (child_te = common.Util.demand(child_te));
	}

	private Object child__G_0;
	public final silver.definition.core.TSemi_t getChild__G_0() {
		return (silver.definition.core.TSemi_t) (child__G_0 = common.Util.demand(child__G_0));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i__G_5: return getChild__G_5();
			case i__G_4: return getChild__G_4();
			case i_a: return getChild_a();
			case i__G_2: return getChild__G_2();
			case i_te: return getChild_te();
			case i__G_0: return getChild__G_0();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i__G_5: return child__G_5;
			case i__G_4: return child__G_4;
			case i_a: return child_a;
			case i__G_2: return child__G_2;
			case i_te: return child_te;
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
		return ((silver.definition.core.NProductionStmt)new silver.definition.core.PlocalAttributeDcl(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return new silver.definition.core.TLocal_kwd((new common.StringCatter("local")), (core.NLocation)((core.NLocation)new core.Ploc((new common.StringCatter("??")), Integer.valueOf((int)-1), Integer.valueOf((int)-1), Integer.valueOf((int)-1), Integer.valueOf((int)-1), Integer.valueOf((int)-1), Integer.valueOf((int)-1)))); } }, context.childAsIsLazy(silver.definition.core.PproductionAttributeDcl.i__G_4), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.core.PproductionAttributeDcl.i_a)), context.childAsIsLazy(silver.definition.core.PproductionAttributeDcl.i__G_2), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.core.PproductionAttributeDcl.i_te)), context.childAsIsLazy(silver.definition.core.PproductionAttributeDcl.i__G_0), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NProductionStmt)context.undecorate()).getAnno_core_location()); } }));
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
		return "silver:definition:core:productionAttributeDcl";
	}

	static void initProductionAttributeDefinitions() {
		// top.productionAttributes = forward.defs
		silver.definition.core.PproductionAttributeDcl.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_productionAttributes__ON__silver_definition_core_ProductionStmt] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)context.forward().synthesized(silver.definition.core.Init.silver_definition_env_defs__ON__silver_definition_core_ProductionStmt)); } };
		// top.defs = []
		silver.definition.core.PproductionAttributeDcl.synthesizedAttributes[silver.definition.core.Init.silver_definition_env_defs__ON__silver_definition_core_ProductionStmt] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } };
		// top.errors <- if ! top.frame.permitProductionAttributes then [ err(top.location, "Production attributes are not valid in this context.") ] else []
		if(silver.definition.core.PproductionAttributeDcl.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt] == null)
			silver.definition.core.PproductionAttributeDcl.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt] = new silver.definition.core.CAerrors(silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt);
		((common.CollectionAttribute)silver.definition.core.PproductionAttributeDcl.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt]).addPiece(new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((!((Boolean)((silver.definition.core.NBlockContext)context.inherited(silver.definition.core.Init.silver_definition_core_frame__ON__silver_definition_core_ProductionStmt)).decorate(context, (common.Lazy[])null).synthesized(silver.definition.core.Init.silver_definition_core_permitProductionAttributes__ON__silver_definition_core_BlockContext))) ? ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NMessage)new silver.definition.core.Perr(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NProductionStmt)context.undecorate()).getAnno_core_location()); } }, (new common.StringCatter("Production attributes are not valid in this context.")))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } })) : ((common.ConsCell)core.Pnil.invoke())); } });

	}

	public static final common.NodeFactory<PproductionAttributeDcl> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PproductionAttributeDcl> {

		@Override
		public PproductionAttributeDcl invoke(final Object[] children, final Object[] annotations) {
			return new PproductionAttributeDcl(children[0], children[1], children[2], children[3], children[4], children[5], annotations[0]);
		}
	};

}
