
package silver.modification.collection;

// top::ProductionStmt ::= 'production' 'attribute' a::Name '::' te::TypeExpr 'with' q::NameOrBOperator ';' 
public final class PcollectionAttributeDclProd extends silver.definition.core.NProductionStmt {

	public static final int i__G_7 = 0;
	public static final int i__G_6 = 1;
	public static final int i_a = 2;
	public static final int i__G_4 = 3;
	public static final int i_te = 4;
	public static final int i__G_2 = 5;
	public static final int i_q = 6;
	public static final int i__G_0 = 7;


	public static final Class<?> childTypes[] = {silver.definition.core.TProduction_kwd.class,silver.definition.core.TAttribute_kwd.class,silver.definition.core.NName.class,silver.definition.core.TColonColon_t.class,silver.definition.type.syntax.NTypeExpr.class,silver.definition.core.TWith_kwd.class,silver.modification.collection.NNameOrBOperator.class,silver.definition.core.TSemi_t.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_modification_collection_collectionAttributeDclProd;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.core.NProductionStmt.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.core.NProductionStmt.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[8][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_a] = new common.Lazy[silver.definition.core.NName.num_inh_attrs];
	childInheritedAttributes[i_te] = new common.Lazy[silver.definition.type.syntax.NTypeExpr.num_inh_attrs];
	childInheritedAttributes[i_q] = new common.Lazy[silver.modification.collection.NNameOrBOperator.num_inh_attrs];

	}

	public PcollectionAttributeDclProd(final Object c__G_7, final Object c__G_6, final Object c_a, final Object c__G_4, final Object c_te, final Object c__G_2, final Object c_q, final Object c__G_0, final Object a_core_location) {
		super(a_core_location);
		this.child__G_7 = c__G_7;
		this.child__G_6 = c__G_6;
		this.child_a = c_a;
		this.child__G_4 = c__G_4;
		this.child_te = c_te;
		this.child__G_2 = c__G_2;
		this.child_q = c_q;
		this.child__G_0 = c__G_0;

	}

	private Object child__G_7;
	public final silver.definition.core.TProduction_kwd getChild__G_7() {
		return (silver.definition.core.TProduction_kwd) (child__G_7 = common.Util.demand(child__G_7));
	}

	private Object child__G_6;
	public final silver.definition.core.TAttribute_kwd getChild__G_6() {
		return (silver.definition.core.TAttribute_kwd) (child__G_6 = common.Util.demand(child__G_6));
	}

	private Object child_a;
	public final silver.definition.core.NName getChild_a() {
		return (silver.definition.core.NName) (child_a = common.Util.demand(child_a));
	}

	private Object child__G_4;
	public final silver.definition.core.TColonColon_t getChild__G_4() {
		return (silver.definition.core.TColonColon_t) (child__G_4 = common.Util.demand(child__G_4));
	}

	private Object child_te;
	public final silver.definition.type.syntax.NTypeExpr getChild_te() {
		return (silver.definition.type.syntax.NTypeExpr) (child_te = common.Util.demand(child_te));
	}

	private Object child__G_2;
	public final silver.definition.core.TWith_kwd getChild__G_2() {
		return (silver.definition.core.TWith_kwd) (child__G_2 = common.Util.demand(child__G_2));
	}

	private Object child_q;
	public final silver.modification.collection.NNameOrBOperator getChild_q() {
		return (silver.modification.collection.NNameOrBOperator) (child_q = common.Util.demand(child_q));
	}

	private Object child__G_0;
	public final silver.definition.core.TSemi_t getChild__G_0() {
		return (silver.definition.core.TSemi_t) (child__G_0 = common.Util.demand(child__G_0));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i__G_7: return getChild__G_7();
			case i__G_6: return getChild__G_6();
			case i_a: return getChild_a();
			case i__G_4: return getChild__G_4();
			case i_te: return getChild_te();
			case i__G_2: return getChild__G_2();
			case i_q: return getChild_q();
			case i__G_0: return getChild__G_0();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i__G_7: return child__G_7;
			case i__G_6: return child__G_6;
			case i_a: return child_a;
			case i__G_4: return child__G_4;
			case i_te: return child_te;
			case i__G_2: return child__G_2;
			case i_q: return child_q;
			case i__G_0: return child__G_0;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 8;
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
		return ((silver.definition.core.NProductionStmt)new silver.definition.core.PproductionAttributeDcl(context.childAsIsLazy(silver.modification.collection.PcollectionAttributeDclProd.i__G_7), context.childAsIsLazy(silver.modification.collection.PcollectionAttributeDclProd.i__G_6), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.modification.collection.PcollectionAttributeDclProd.i_a)), context.childAsIsLazy(silver.modification.collection.PcollectionAttributeDclProd.i__G_4), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.modification.collection.PcollectionAttributeDclProd.i_te)), context.childAsIsLazy(silver.modification.collection.PcollectionAttributeDclProd.i__G_0), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NProductionStmt)context.undecorate()).getAnno_core_location()); } }));
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
		return "silver:modification:collection:collectionAttributeDclProd";
	}

	static void initProductionAttributeDefinitions() {
		// top.pp = "production attribute " ++ a.name ++ " :: " ++ te.pp ++ " with " ++ q.pp ++ " ;"
		silver.modification.collection.PcollectionAttributeDclProd.synthesizedAttributes[silver.definition.core.Init.silver_definition_env_pp__ON__silver_definition_core_ProductionStmt] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return new common.StringCatter((common.StringCatter)(new common.StringCatter("production attribute ")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)context.childDecorated(silver.modification.collection.PcollectionAttributeDclProd.i_a).synthesized(silver.definition.core.Init.silver_definition_core_name__ON__silver_definition_core_Name)), (common.StringCatter)new common.StringCatter((common.StringCatter)(new common.StringCatter(" :: ")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)context.childDecorated(silver.modification.collection.PcollectionAttributeDclProd.i_te).synthesized(silver.definition.type.syntax.Init.silver_definition_env_pp__ON__silver_definition_type_syntax_TypeExpr)), (common.StringCatter)new common.StringCatter((common.StringCatter)(new common.StringCatter(" with ")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)context.childDecorated(silver.modification.collection.PcollectionAttributeDclProd.i_q).synthesized(silver.modification.collection.Init.silver_definition_env_pp__ON__silver_modification_collection_NameOrBOperator)), (common.StringCatter)(new common.StringCatter(" ;")))))))); } };
		// top.productionAttributes = [ localColDef(top.grammarName, a.location, fName, te.typerep, q.operation) ]
		silver.modification.collection.PcollectionAttributeDclProd.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_productionAttributes__ON__silver_definition_core_ProductionStmt] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.env.NDef)silver.modification.collection.PlocalColDef.invoke(context.contextInheritedLazy(silver.definition.core.Init.silver_definition_core_grammarName__ON__silver_definition_core_ProductionStmt), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NName)context.childDecorated(silver.modification.collection.PcollectionAttributeDclProd.i_a).undecorate()).getAnno_core_location()); } }, context.localAsIsLazy(silver.modification.collection.Init.fName__ON__silver_modification_collection_collectionAttributeDclProd), context.childDecoratedSynthesizedLazy(silver.modification.collection.PcollectionAttributeDclProd.i_te, silver.definition.type.syntax.Init.silver_definition_env_typerep__ON__silver_definition_type_syntax_TypeExpr), context.childDecoratedSynthesizedLazy(silver.modification.collection.PcollectionAttributeDclProd.i_q, silver.modification.collection.Init.silver_modification_collection_operation__ON__silver_modification_collection_NameOrBOperator))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } })); } };
		// fName = top.frame.fullName ++ ":local:" ++ a.name
		silver.modification.collection.PcollectionAttributeDclProd.localAttributes[silver.modification.collection.Init.fName__ON__silver_modification_collection_collectionAttributeDclProd] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return new common.StringCatter((common.StringCatter)((common.StringCatter)((silver.definition.core.NBlockContext)context.inherited(silver.definition.core.Init.silver_definition_core_frame__ON__silver_definition_core_ProductionStmt)).decorate(context, (common.Lazy[])null).synthesized(silver.definition.core.Init.silver_definition_env_fullName__ON__silver_definition_core_BlockContext)), (common.StringCatter)new common.StringCatter((common.StringCatter)(new common.StringCatter(":local:")), (common.StringCatter)((common.StringCatter)context.childDecorated(silver.modification.collection.PcollectionAttributeDclProd.i_a).synthesized(silver.definition.core.Init.silver_definition_core_name__ON__silver_definition_core_Name)))); } };
		// top.defs = []
		silver.modification.collection.PcollectionAttributeDclProd.synthesizedAttributes[silver.definition.core.Init.silver_definition_env_defs__ON__silver_definition_core_ProductionStmt] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } };
		// top.errors := te.errors ++ q.errors
		if(silver.modification.collection.PcollectionAttributeDclProd.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt] == null)
			silver.modification.collection.PcollectionAttributeDclProd.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt] = new silver.definition.core.CAerrors(silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt);
		((common.CollectionAttribute)silver.modification.collection.PcollectionAttributeDclProd.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt]).setBase(new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pappend.invoke(context.childDecoratedSynthesizedLazy(silver.modification.collection.PcollectionAttributeDclProd.i_te, silver.definition.type.syntax.Init.silver_definition_core_errors__ON__silver_definition_type_syntax_TypeExpr), context.childDecoratedSynthesizedLazy(silver.modification.collection.PcollectionAttributeDclProd.i_q, silver.modification.collection.Init.silver_definition_core_errors__ON__silver_modification_collection_NameOrBOperator))); } });
		// top.errors <- if length(getValueDclAll(fName, top.env)) > 1 then [ err(a.location, "Value '" ++ fName ++ "' is already bound.") ] else []
		if(silver.modification.collection.PcollectionAttributeDclProd.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt] == null)
			silver.modification.collection.PcollectionAttributeDclProd.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt] = new silver.definition.core.CAerrors(silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt);
		((common.CollectionAttribute)silver.modification.collection.PcollectionAttributeDclProd.synthesizedAttributes[silver.definition.core.Init.silver_definition_core_errors__ON__silver_definition_core_ProductionStmt]).addPiece(new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((((Integer)core.PlistLength.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)silver.definition.env.PgetValueDclAll.invoke(context.localAsIsLazy(silver.modification.collection.Init.fName__ON__silver_modification_collection_collectionAttributeDclProd), context.contextInheritedLazy(silver.definition.core.Init.silver_definition_env_env__ON__silver_definition_core_ProductionStmt))); } })) > Integer.valueOf((int)1)) ? ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NMessage)new silver.definition.core.Perr(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NName)context.childDecorated(silver.modification.collection.PcollectionAttributeDclProd.i_a).undecorate()).getAnno_core_location()); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return new common.StringCatter((common.StringCatter)(new common.StringCatter("Value '")), (common.StringCatter)new common.StringCatter((common.StringCatter)((common.StringCatter)context.localAsIs(silver.modification.collection.Init.fName__ON__silver_modification_collection_collectionAttributeDclProd)), (common.StringCatter)(new common.StringCatter("' is already bound.")))); } })); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } })) : ((common.ConsCell)core.Pnil.invoke())); } });
		// q.operatorForType = te.typerep
		silver.modification.collection.PcollectionAttributeDclProd.childInheritedAttributes[silver.modification.collection.PcollectionAttributeDclProd.i_q][silver.modification.collection.Init.silver_modification_collection_operatorForType__ON__silver_modification_collection_NameOrBOperator] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((silver.definition.type.NType)context.childDecorated(silver.modification.collection.PcollectionAttributeDclProd.i_te).synthesized(silver.definition.type.syntax.Init.silver_definition_env_typerep__ON__silver_definition_type_syntax_TypeExpr)); } };

	}

	public static final common.NodeFactory<PcollectionAttributeDclProd> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PcollectionAttributeDclProd> {

		@Override
		public PcollectionAttributeDclProd invoke(final Object[] children, final Object[] annotations) {
			return new PcollectionAttributeDclProd(children[0], children[1], children[2], children[3], children[4], children[5], children[6], children[7], annotations[0]);
		}
	};

}
