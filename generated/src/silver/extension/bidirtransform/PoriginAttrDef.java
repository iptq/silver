
package silver.extension.bidirtransform;

// top::AGDcl ::= qn::QName 
public final class PoriginAttrDef extends silver.definition.core.NAGDcl {

	public static final int i_qn = 0;


	public static final Class<?> childTypes[] = {silver.definition.core.NQName.class};

	public static final int num_local_attrs = Init.count_local__ON__silver_extension_bidirtransform_originAttrDef;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[] forwardInheritedAttributes = new common.Lazy[silver.definition.core.NAGDcl.num_inh_attrs];

	public static final common.Lazy[] synthesizedAttributes = new common.Lazy[silver.definition.core.NAGDcl.num_syn_attrs];
	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[1][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static {
	childInheritedAttributes[i_qn] = new common.Lazy[silver.definition.core.NQName.num_inh_attrs];

	}

	public PoriginAttrDef(final Object c_qn, final Object a_core_location) {
		super(a_core_location);
		this.child_qn = c_qn;

	}

	private Object child_qn;
	public final silver.definition.core.NQName getChild_qn() {
		return (silver.definition.core.NQName) (child_qn = common.Util.demand(child_qn));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_qn: return getChild_qn();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_qn: return child_qn;

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
		return ((silver.definition.core.NAGDcl)core.Pfoldl.invoke((new common.NodeFactory<silver.definition.core.NAGDcl>() {
  public final silver.definition.core.NAGDcl invoke(final Object[] args, final Object[] namedArgs) {
    final Object __SV_LAMBDA_PARAM_12587_agDcls = args[0];
final Object __SV_LAMBDA_PARAM_12588_ns = args[1];

    return ((silver.definition.core.NAGDcl)new silver.definition.core.PappendAGDcl(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return (((Boolean)core.Pnull.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)((common.DecoratedNode)common.Util.demand(__SV_LAMBDA_PARAM_12588_ns)).synthesized(silver.definition.env.Init.silver_definition_type_inputTypes__ON__silver_definition_env_NamedSignature)); } })) ? ((silver.definition.core.NAGDcl)new silver.definition.core.PemptyAGDcl(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NAGDcl)context.undecorate()).getAnno_core_location()); } })) : (((Boolean)silver.extension.bidirtransform.PhasNamedAttr.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)((silver.definition.type.NType)core.Phead.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)((common.DecoratedNode)common.Util.demand(__SV_LAMBDA_PARAM_12588_ns)).synthesized(silver.definition.env.Init.silver_definition_type_inputTypes__ON__silver_definition_env_NamedSignature)); } })).decorate(context, (common.Lazy[])null).synthesized(silver.definition.env.Init.silver_definition_env_typeName__ON__silver_definition_type_Type)); } }, context.contextInheritedLazy(silver.definition.core.Init.silver_definition_env_env__ON__silver_definition_core_AGDcl), context.childDecoratedSynthesizedLazy(silver.extension.bidirtransform.PoriginAttrDef.i_qn, silver.definition.core.Init.silver_definition_core_name__ON__silver_definition_core_QName))) ? ((silver.definition.core.NAGDcl)new silver.extension.bidirtransform.PaspectProdStmt(((common.DecoratedNode)common.Util.demand(__SV_LAMBDA_PARAM_12588_ns)), (new common.NodeFactory<silver.definition.core.NProductionStmt>() {
  public final silver.definition.core.NProductionStmt invoke(final Object[] args, final Object[] namedArgs) {
    final Object __SV_LAMBDA_PARAM_12594_ns = args[0];

    return ((silver.definition.core.NProductionStmt)new silver.extension.bidirtransform.PattribDef(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)((silver.definition.env.NNamedSignatureElement)((common.DecoratedNode)common.Util.demand(__SV_LAMBDA_PARAM_12594_ns)).synthesized(silver.definition.env.Init.silver_definition_env_outputElement__ON__silver_definition_env_NamedSignature)).decorate(context, (common.Lazy[])null).synthesized(silver.definition.env.Init.silver_definition_env_elementName__ON__silver_definition_env_NamedSignatureElement)); } }, context.childDecoratedSynthesizedLazy(silver.extension.bidirtransform.PoriginAttrDef.i_qn, silver.definition.core.Init.silver_definition_core_name__ON__silver_definition_core_QName), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NExpr)new silver.extension.bidirtransform.PexprAccess(context.childDecoratedSynthesizedLazy(silver.extension.bidirtransform.PoriginAttrDef.i_qn, silver.definition.core.Init.silver_definition_core_name__ON__silver_definition_core_QName), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)core.Phead.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)((common.DecoratedNode)common.Util.demand(__SV_LAMBDA_PARAM_12594_ns)).synthesized(silver.definition.env.Init.silver_definition_env_inputNames__ON__silver_definition_env_NamedSignature)); } })); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NAGDcl)context.undecorate()).getAnno_core_location()); } })); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NAGDcl)context.undecorate()).getAnno_core_location()); } }));
  }
}), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NAGDcl)context.undecorate()).getAnno_core_location()); } })) : ((silver.definition.core.NAGDcl)new silver.definition.core.PemptyAGDcl(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NAGDcl)context.undecorate()).getAnno_core_location()); } })))); } }, ((silver.definition.core.NAGDcl)common.Util.demand(__SV_LAMBDA_PARAM_12587_agDcls)), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NAGDcl)context.undecorate()).getAnno_core_location()); } }));
  }
}), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NAGDcl)new silver.definition.core.PemptyAGDcl(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NLocation)((silver.definition.core.NAGDcl)context.undecorate()).getAnno_core_location()); } })); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)silver.extension.bidirtransform.PfilterSigs.invoke((new common.StringCatter("Origin")), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)silver.extension.bidirtransform.PprodsFromDefs.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)((common.DecoratedNode)context.inherited(silver.definition.core.Init.silver_definition_env_env__ON__silver_definition_core_AGDcl)).synthesized(silver.extension.bidirtransform.Init.silver_extension_bidirtransform_allDefs__ON__silver_definition_env_Env)); } })); } })); } }));
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
		return "silver:extension:bidirtransform:originAttrDef";
	}

	static void initProductionAttributeDefinitions() {

		// top.defs = []
		silver.extension.bidirtransform.PoriginAttrDef.synthesizedAttributes[silver.definition.core.Init.silver_definition_env_defs__ON__silver_definition_core_AGDcl] = new common.Lazy() { public final Object eval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } };

	}

	public static final common.NodeFactory<PoriginAttrDef> factory = new Factory();

	public static final class Factory extends common.NodeFactory<PoriginAttrDef> {

		@Override
		public PoriginAttrDef invoke(final Object[] children, final Object[] annotations) {
			return new PoriginAttrDef(children[0], annotations[0]);
		}
	};

}
