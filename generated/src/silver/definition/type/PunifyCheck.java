
package silver.definition.type;

public final class PunifyCheck extends common.FunctionNode {

	public static final int i_te1 = 0;
	public static final int i_te2 = 1;
	public static final int i_s = 2;


	public static final Class<?> childTypes[] = { silver.definition.type.NType.class,silver.definition.type.NType.class,silver.definition.type.NSubstitution.class };

	public static final int num_local_attrs = Init.count_local__ON__silver_definition_type_unifyCheck;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[3][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static{
	childInheritedAttributes[i_te1] = new common.Lazy[silver.definition.type.NType.num_inh_attrs];
	childInheritedAttributes[i_te2] = new common.Lazy[silver.definition.type.NType.num_inh_attrs];
	childInheritedAttributes[i_s] = new common.Lazy[silver.definition.type.NSubstitution.num_inh_attrs];

	}

	public PunifyCheck(final Object c_te1, final Object c_te2, final Object c_s) {
		this.child_te1 = c_te1;
		this.child_te2 = c_te2;
		this.child_s = c_s;

	}

	private Object child_te1;
	public final silver.definition.type.NType getChild_te1() {
		return (silver.definition.type.NType) (child_te1 = common.Util.demand(child_te1));
	}

	private Object child_te2;
	public final silver.definition.type.NType getChild_te2() {
		return (silver.definition.type.NType) (child_te2 = common.Util.demand(child_te2));
	}

	private Object child_s;
	public final silver.definition.type.NSubstitution getChild_s() {
		return (silver.definition.type.NSubstitution) (child_s = common.Util.demand(child_s));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_te1: return getChild_te1();
			case i_te2: return getChild_te2();
			case i_s: return getChild_s();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_te1: return child_te1;
			case i_te2: return child_te2;
			case i_s: return child_s;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 3;
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
		return "silver:definition:type:unifyCheck";
	}

	public static silver.definition.type.NSubstitution invoke(final Object c_te1, final Object c_te2, final Object c_s) {
		try {
		final common.DecoratedNode context = new PunifyCheck(c_te1, c_te2, c_s).decorate();
		//composeSubst(ignoreFailure(s), unify(performSubstitution(te1, s), performSubstitution(te2, s)))
		return (silver.definition.type.NSubstitution)(((silver.definition.type.NSubstitution)silver.definition.type.PcomposeSubst.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.type.NSubstitution)silver.definition.type.PignoreFailure.invoke(common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.type.PunifyCheck.i_s)))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.type.NSubstitution)silver.definition.type.Punify.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.type.NType)silver.definition.type.PperformSubstitution.invoke(common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.type.PunifyCheck.i_te1)), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.type.PunifyCheck.i_s)))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.type.NType)silver.definition.type.PperformSubstitution.invoke(common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.type.PunifyCheck.i_te2)), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.definition.type.PunifyCheck.i_s)))); } })); } })));

		} catch(Throwable t) {
			throw new common.exceptions.TraceException("Error while evaluating function silver:definition:type:unifyCheck", t);
		}
	}

	public static final common.NodeFactory<silver.definition.type.NSubstitution> factory = new Factory();

	public static final class Factory extends common.NodeFactory<silver.definition.type.NSubstitution> {
		@Override
		public silver.definition.type.NSubstitution invoke(final Object[] children, final Object[] namedNotApplicable) {
			return PunifyCheck.invoke(children[0], children[1], children[2]);
		}
	};
}