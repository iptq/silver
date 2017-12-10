
package silver.extension.convenience;

// top::ProductionStmt ::= pk::'production' ak::'attribute' a::Name ht::'::' te::TypeExpr eq::'=' v::Expr sm::';' 
public final class PshortProductionDeclwKwds extends silver.definition.core.NProductionStmt {

	public static final int i_pk = 0;
	public static final int i_ak = 1;
	public static final int i_a = 2;
	public static final int i_ht = 3;
	public static final int i_te = 4;
	public static final int i_eq = 5;
	public static final int i_v = 6;
	public static final int i_sm = 7;


	public static final Class<?> childTypes[] = {silver.definition.core.TProduction_kwd.class,silver.definition.core.TAttribute_kwd.class,silver.definition.core.NName.class,silver.definition.core.TColonColon_t.class,silver.definition.type.syntax.NTypeExpr.class,silver.definition.core.TEqual_t.class,silver.definition.core.NExpr.class,silver.definition.core.TSemi_t.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_extension_convenience_shortProductionDeclwKwds;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.core.NProductionStmt.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.core.NProductionStmt.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[8][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_a] = new common.Lazy[silver.definition.core.NName.num_inh_attrs];
	childInheritedAttributes[i_te] = new common.Lazy[silver.definition.type.syntax.NTypeExpr.num_inh_attrs];
	childInheritedAttributes[i_v] = new common.Lazy[silver.definition.core.NExpr.num_inh_attrs];

	}

	public PshortProductionDeclwKwds(final Object c_pk, final Object c_ak, final Object c_a, final Object c_ht, final Object c_te, final Object c_eq, final Object c_v, final Object c_sm, final Object a_core_location) {
		super(a_core_location);
		this.child_pk = c_pk;
		this.child_ak = c_ak;
		this.child_a = c_a;
		this.child_ht = c_ht;
		this.child_te = c_te;
		this.child_eq = c_eq;
		this.child_v = c_v;
		this.child_sm = c_sm;

	}

	private Object child_pk;
	public final silver.definition.core.TProduction_kwd getChild_pk() {
		return (silver.definition.core.TProduction_kwd) (child_pk = common.Util.demand(child_pk));
	}

	private Object child_ak;
	public final silver.definition.core.TAttribute_kwd getChild_ak() {
		return (silver.definition.core.TAttribute_kwd) (child_ak = common.Util.demand(child_ak));
	}

	private Object child_a;
	public final silver.definition.core.NName getChild_a() {
		return (silver.definition.core.NName) (child_a = common.Util.demand(child_a));
	}

	private Object child_ht;
	public final silver.definition.core.TColonColon_t getChild_ht() {
		return (silver.definition.core.TColonColon_t) (child_ht = common.Util.demand(child_ht));
	}

	private Object child_te;
	public final silver.definition.type.syntax.NTypeExpr getChild_te() {
		return (silver.definition.type.syntax.NTypeExpr) (child_te = common.Util.demand(child_te));
	}

	private Object child_eq;
	public final silver.definition.core.TEqual_t getChild_eq() {
		return (silver.definition.core.TEqual_t) (child_eq = common.Util.demand(child_eq));
	}

	private Object child_v;
	public final silver.definition.core.NExpr getChild_v() {
		return (silver.definition.core.NExpr) (child_v = common.Util.demand(child_v));
	}

	private Object child_sm;
	public final silver.definition.core.TSemi_t getChild_sm() {
		return (silver.definition.core.TSemi_t) (child_sm = common.Util.demand(child_sm));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_pk: return getChild_pk();
			case i_ak: return getChild_ak();
			case i_a: return getChild_a();
			case i_ht: return getChild_ht();
			case i_te: return getChild_te();
			case i_eq: return getChild_eq();
			case i_v: return getChild_v();
			case i_sm: return getChild_sm();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_pk: return child_pk;
			case i_ak: return child_ak;
			case i_a: return child_a;
			case i_ht: return child_ht;
			case i_te: return child_te;
			case i_eq: return child_eq;
			case i_v: return child_v;
			case i_sm: return child_sm;

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
		return ((silver.definition.core.NProductionStmt)new silver.definition.core.PproductionStmtAppend(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NProductionStmt)new silver.definition.core.PproductionAttributeDcl(context.childAsIsLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_pk), context.childAsIsLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_ak), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_a)), context.childAsIsLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_ht), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_te)), context.childAsIsLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_sm), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NProductionStmt)context.undecorate()).getAnno_core_location()); } })); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NProductionStmt)new silver.definition.core.PvalueEq(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NQName)new silver.definition.core.PqNameId(common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_a)), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NName)context.childDecorated(silver.extension.convenience.PshortProductionDeclwKwds.i_a).undecorate()).getAnno_core_location()); } })); } }, context.childAsIsLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_eq), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_v)), context.childAsIsLazy(silver.extension.convenience.PshortProductionDeclwKwds.i_sm), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NExpr)context.childDecorated(silver.extension.convenience.PshortProductionDeclwKwds.i_v).undecorate()).getAnno_core_location()); } })); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NProductionStmt)context.undecorate()).getAnno_core_location()); } }));
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
		return "silver:extension:convenience:shortProductionDeclwKwds";
	}

	static void initProductionAttributeDefinitions() {

	}

	public static final common.NodeFactory<PshortProductionDeclwKwds> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PshortProductionDeclwKwds> {

		@Override
		public PshortProductionDeclwKwds invoke(final Object[] children, final Object[] annotations) {
			return new PshortProductionDeclwKwds(children[0], children[1], children[2], children[3], children[4], children[5], children[6], children[7], annotations[0]);
		}
	};

}