
package silver.translation.java.driver;

// top::CmdArgs ::= rest::CmdArgs 
public final class PxjFlag extends silver.util.cmdargs.NCmdArgs {

	public static final int i_rest = 0;


	public static final Class<?> childTypes[] = {silver.util.cmdargs.NCmdArgs.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_translation_java_driver_xjFlag;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.util.cmdargs.NCmdArgs.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.util.cmdargs.NCmdArgs.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[1][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_rest] = new common.Lazy[silver.util.cmdargs.NCmdArgs.num_inh_attrs];

	}

	public PxjFlag(final Object c_rest) {
		super();
		this.child_rest = c_rest;

	}

	private Object child_rest;
	public final silver.util.cmdargs.NCmdArgs getChild_rest() {
		return (silver.util.cmdargs.NCmdArgs) (child_rest = common.Util.demand(child_rest));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_rest: return getChild_rest();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_rest: return child_rest;

			default: return null;
		}
	}

	@Override
	public final int getNumberOfChildren() {
		return 1;
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
		return context.childDecorated(silver.translation.java.driver.PxjFlag.i_rest).undecorate();
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
		return "silver:translation:java:driver:xjFlag";
	}

	static void initProductionAttributeDefinitions() {
		// top.noJavaGeneration = true
		silver.translation.java.driver.PxjFlag.synthesizedAttributes[silver.translation.java.driver.Init.silver_translation_java_driver_noJavaGeneration__ON__silver_util_cmdargs_CmdArgs] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return true; } };

	}

	public static final common.NodeFactory<PxjFlag> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PxjFlag> {

		@Override
		public PxjFlag invoke(final Object[] children, final Object[] annotations) {
			return new PxjFlag(children[0]);
		}
	};

}
