
package silver.extension.testing;

public final class PmkEqualityTestExprCS extends common.FunctionNode {

	public static final int i_valueType = 0;
	public static final int i_l = 1;


	public static final Class<?> childTypes[] = { silver.definition.type.syntax.NTypeExpr.class,core.NLocation.class };

	public static final int num_local_attrs = Init.count_local__ON__silver_extension_testing_mkEqualityTestExprCS;
	public static final String[] occurs_local = new String[num_local_attrs];

	public static final common.Lazy[][] childInheritedAttributes = new common.Lazy[2][];

	public static final common.Lazy[] localAttributes = new common.Lazy[num_local_attrs];
	public static final common.Lazy[][] localInheritedAttributes = new common.Lazy[num_local_attrs][];

	static{
	childInheritedAttributes[i_valueType] = new common.Lazy[silver.definition.type.syntax.NTypeExpr.num_inh_attrs];
	childInheritedAttributes[i_l] = new common.Lazy[core.NLocation.num_inh_attrs];

	}

	public PmkEqualityTestExprCS(final Object c_valueType, final Object c_l) {
		this.child_valueType = c_valueType;
		this.child_l = c_l;

	}

	private Object child_valueType;
	public final silver.definition.type.syntax.NTypeExpr getChild_valueType() {
		return (silver.definition.type.syntax.NTypeExpr) (child_valueType = common.Util.demand(child_valueType));
	}

	private Object child_l;
	public final core.NLocation getChild_l() {
		return (core.NLocation) (child_l = common.Util.demand(child_l));
	}



	@Override
	public Object getChild(final int index) {
		switch(index) {
			case i_valueType: return getChild_valueType();
			case i_l: return getChild_l();

			default: return null;
		}
	}

	@Override
	public Object getChildLazy(final int index) {
		switch(index) {
			case i_valueType: return child_valueType;
			case i_l: return child_l;

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
		return "silver:extension:testing:mkEqualityTestExprCS";
	}

	public static core.NMaybe invoke(final Object c_valueType, final Object c_l) {
		try {
		final common.DecoratedNode context = new PmkEqualityTestExprCS(c_valueType, c_l).decorate();
		//case functionNameForBaseTypesCS(valueType, "equals") of just(btt) -> just(mkStrFunctionInvocation(l, btt, [ mkNameExpr("value", l), mkNameExpr("expected", l) ])) | nothing() -> case valueType of listTypeExpr(_, elemType, _) -> case functionNameForBaseTypesCS(elemType, "equals") of just(btt) -> just(mkStrFunctionInvocation(l, "equalsList", [ mkNameExpr(btt, l), mkNameExpr("value", l), mkNameExpr("expected", l) ])) | _ -> nothing() end | _ -> nothing() end end
		return (core.NMaybe)(new common.PatternLazy<common.DecoratedNode, core.NMaybe>() { public final core.NMaybe eval(final common.DecoratedNode context, common.DecoratedNode scrutineeIter) {while(true) {final common.DecoratedNode scrutinee = scrutineeIter; final common.Node scrutineeNode = scrutinee.undecorate(); if(scrutineeNode instanceof core.Pjust) { final common.Thunk<Object> __SV_LOCAL___pv8625___sv_pv_8624_btt = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return (common.StringCatter)scrutinee.childAsIs(0); } };
 return (core.NMaybe)((core.NMaybe)(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { final common.Thunk<Object> __SV_LOCAL_8626_btt = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)(__SV_LOCAL___pv8625___sv_pv_8624_btt.eval())); } };
return ((core.NMaybe)new core.Pjust(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NExpr)silver.definition.core.PmkStrFunctionInvocation.invoke(common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.testing.PmkEqualityTestExprCS.i_l)), __SV_LOCAL_8626_btt, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NExpr)silver.extension.testing.PmkNameExpr.invoke((new common.StringCatter("value")), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.testing.PmkEqualityTestExprCS.i_l)))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NExpr)silver.extension.testing.PmkNameExpr.invoke((new common.StringCatter("expected")), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.testing.PmkEqualityTestExprCS.i_l)))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } })); } })); } })); } })); } }).eval()); }
else if(scrutineeNode instanceof core.Pnothing) {  return (core.NMaybe)((core.NMaybe)(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { final common.Thunk<Object> __SV_LOCAL_8627___fail_8628 = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NMaybe)new core.Pnothing()); } };
return new common.PatternLazy<common.DecoratedNode, core.NMaybe>() { public final core.NMaybe eval(final common.DecoratedNode context, common.DecoratedNode scrutineeIter) {while(true) {final common.DecoratedNode scrutinee = scrutineeIter; final common.Node scrutineeNode = scrutinee.undecorate(); if(scrutineeNode instanceof silver.extension.list.PlistTypeExpr) { final common.Thunk<Object> __SV_LOCAL___pv8645___sv_tmp_pv_8646 = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return (silver.extension.list.TLSqr_t)scrutinee.childAsIs(0); } };
final common.Thunk<Object> __SV_LOCAL___pv8647___sv_pv_8644_elemType = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return (common.DecoratedNode)scrutinee.childDecorated(1); } };
final common.Thunk<Object> __SV_LOCAL___pv8648___sv_tmp_pv_8649 = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return (silver.extension.list.TRSqr_t)scrutinee.childAsIs(2); } };
 return (core.NMaybe)((core.NMaybe)(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { final common.Thunk<Object> __SV_LOCAL_8650_elemType = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.DecoratedNode)(__SV_LOCAL___pv8647___sv_pv_8644_elemType.eval())); } };
return ((core.NMaybe)(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { final common.Thunk<Object> __SV_LOCAL_8651___fail_8652 = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((core.NMaybe)new core.Pnothing()); } };
return new common.PatternLazy<common.DecoratedNode, core.NMaybe>() { public final core.NMaybe eval(final common.DecoratedNode context, common.DecoratedNode scrutineeIter) {while(true) {final common.DecoratedNode scrutinee = scrutineeIter; final common.Node scrutineeNode = scrutinee.undecorate(); if(scrutineeNode instanceof core.Pjust) { final common.Thunk<Object> __SV_LOCAL___pv8654___sv_pv_8653_btt = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return (common.StringCatter)scrutinee.childAsIs(0); } };
 return (core.NMaybe)((core.NMaybe)(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { final common.Thunk<Object> __SV_LOCAL_8655_btt = new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.StringCatter)(__SV_LOCAL___pv8654___sv_pv_8653_btt.eval())); } };
return ((core.NMaybe)new core.Pjust(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NExpr)silver.definition.core.PmkStrFunctionInvocation.invoke(common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.testing.PmkEqualityTestExprCS.i_l)), (new common.StringCatter("equalsList")), new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NExpr)silver.extension.testing.PmkNameExpr.invoke(__SV_LOCAL_8655_btt, common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.testing.PmkEqualityTestExprCS.i_l)))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NExpr)silver.extension.testing.PmkNameExpr.invoke((new common.StringCatter("value")), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.testing.PmkEqualityTestExprCS.i_l)))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pcons.invoke(new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((silver.definition.core.NExpr)silver.extension.testing.PmkNameExpr.invoke((new common.StringCatter("expected")), common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.testing.PmkEqualityTestExprCS.i_l)))); } }, new common.Thunk<Object>(context) { public final Object doEval(final common.DecoratedNode context) { return ((common.ConsCell)core.Pnil.invoke()); } })); } })); } })); } })); } })); } }).eval()); }if(!scrutineeIter.undecorate().hasForward()) break;scrutineeIter = scrutineeIter.forward();}return ((core.NMaybe)(__SV_LOCAL_8651___fail_8652.eval()));}}.eval(context, (common.DecoratedNode)((core.NMaybe)silver.extension.testing.PfunctionNameForBaseTypesCS.invoke(common.Thunk.transformUndecorate(__SV_LOCAL_8650_elemType), (new common.StringCatter("equals")))).decorate(context, (common.Lazy[])null)); } }).eval()); } }).eval()); }if(!scrutineeIter.undecorate().hasForward()) break;scrutineeIter = scrutineeIter.forward();}return ((core.NMaybe)(__SV_LOCAL_8627___fail_8628.eval()));}}.eval(context, (common.DecoratedNode)context.childDecorated(silver.extension.testing.PmkEqualityTestExprCS.i_valueType)); } }).eval()); }if(!scrutineeIter.undecorate().hasForward()) break;scrutineeIter = scrutineeIter.forward();}return ((core.NMaybe)core.Perror.invoke((new common.StringCatter("Error: pattern match failed at silver:extension:testing 'EqualityTest.sv', 232, 4, 244, 7, 10124, 10696\n"))));}}.eval(context, (common.DecoratedNode)((core.NMaybe)silver.extension.testing.PfunctionNameForBaseTypesCS.invoke(common.Thunk.transformUndecorate(context.childDecoratedLazy(silver.extension.testing.PmkEqualityTestExprCS.i_valueType)), (new common.StringCatter("equals")))).decorate(context, (common.Lazy[])null)));

		} catch(Throwable t) {
			throw new common.exceptions.TraceException("Error while evaluating function silver:extension:testing:mkEqualityTestExprCS", t);
		}
	}

	public static final common.NodeFactory<core.NMaybe> factory = new Factory();

	public static final class Factory extends common.NodeFactory<core.NMaybe> {
		@Override
		public core.NMaybe invoke(final Object[] children, final Object[] namedNotApplicable) {
			return PmkEqualityTestExprCS.invoke(children[0], children[1]);
		}
	};
}