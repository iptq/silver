
package lib.extcore;

public final class PnotEqualsBoolean extends common.FunctionNode {

	public static final int i_a = 0;
	public static final int i_b = 1;


	public static final Class<?> childTypes[] = { Boolean.class,Boolean.class };

	public static final int num_local_attrs = Init.count_local__ON__lib_extcore_notEqualsBoolean;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[2][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static{

	}

	public PnotEqualsBoolean(final Object c_a, final Object c_b) {
		this.child_a = c_a;
		this.child_b = c_b;

	}

	private Object child_a;
	public final Boolean getChild_a() {
		return (Boolean) (child_a = common.Util.demand(child_a));
	}

	private Object child_b;
	public final Boolean getChild_b() {
		return (Boolean) (child_b = common.Util.demand(child_b));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_a: return getChild_a();
			case i_b: return getChild_b();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_a: return child_a;
			case i_b: return child_b;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 2;
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
		return "lib:extcore:notEqualsBoolean";
	}

	public static Boolean invoke(final Object c_a, final Object c_b) {
		try {
		final common.DecoratedNode context = new PnotEqualsBoolean(c_a, c_b).decorate();
		//a != b
		return (Boolean)(!((Boolean)context.childAsIs(lib.extcore.PnotEqualsBoolean.i_a)).equals(((Boolean)context.childAsIs(lib.extcore.PnotEqualsBoolean.i_b))));

		} catch(Throwable t) {
			throw new common.exceptions.TraceException("Error while evaluating function lib:extcore:notEqualsBoolean", t);
		}
	}

	public static final common.NodeFactory<Boolean> factory = new Factory();

	public static final class Factory extends common.NodeFactory<Boolean> {
		@Override
		public Boolean invoke(final Object[] children, final Object[] namedNotApplicable) {
			return PnotEqualsBoolean.invoke(children[0], children[1]);
		}
	};
}