//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.27 at 10:55:28 CEST 
//


package org.iris.client.settings.xml.impl;

public class IrisclientTypeImpl implements org.iris.client.settings.xml.IrisclientType, com.sun.xml.bind.JAXBObject, org.iris.client.settings.xml.impl.runtime.UnmarshallableObject, org.iris.client.settings.xml.impl.runtime.XMLSerializable, org.iris.client.settings.xml.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Irisserver;
    protected com.sun.xml.bind.util.ListImpl _Lookandfeel;
    protected boolean has_Pingservices;
    protected short _Pingservices;
    protected java.lang.String _Title;
    protected org.iris.client.settings.xml.VersioninfoType _Versioninfo;
    public final static java.lang.Class version = (org.iris.client.settings.xml.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (org.iris.client.settings.xml.IrisclientType.class);
    }

    protected com.sun.xml.bind.util.ListImpl _getIrisserver() {
        if (_Irisserver == null) {
            _Irisserver = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Irisserver;
    }

    public java.util.List getIrisserver() {
        return _getIrisserver();
    }

    protected com.sun.xml.bind.util.ListImpl _getLookandfeel() {
        if (_Lookandfeel == null) {
            _Lookandfeel = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Lookandfeel;
    }

    public java.util.List getLookandfeel() {
        return _getLookandfeel();
    }

    public short getPingservices() {
        return _Pingservices;
    }

    public void setPingservices(short value) {
        _Pingservices = value;
        has_Pingservices = true;
    }

    public java.lang.String getTitle() {
        return _Title;
    }

    public void setTitle(java.lang.String value) {
        _Title = value;
    }

    public org.iris.client.settings.xml.VersioninfoType getVersioninfo() {
        return _Versioninfo;
    }

    public void setVersioninfo(org.iris.client.settings.xml.VersioninfoType value) {
        _Versioninfo = value;
    }

    public org.iris.client.settings.xml.impl.runtime.UnmarshallingEventHandler createUnmarshaller(org.iris.client.settings.xml.impl.runtime.UnmarshallingContext context) {
        return new org.iris.client.settings.xml.impl.IrisclientTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(org.iris.client.settings.xml.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Irisserver == null)? 0 :_Irisserver.size());
        int idx2 = 0;
        final int len2 = ((_Lookandfeel == null)? 0 :_Lookandfeel.size());
        if (!has_Pingservices) {
            context.reportError(com.sun.xml.bind.serializer.Util.createMissingObjectError(this, "Pingservices"));
        }
        context.startElement("", "versioninfo");
        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Versioninfo), "Versioninfo");
        context.endNamespaceDecls();
        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Versioninfo), "Versioninfo");
        context.endAttributes();
        context.childAsBody(((com.sun.xml.bind.JAXBObject) _Versioninfo), "Versioninfo");
        context.endElement();
        context.startElement("", "title");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(((java.lang.String) _Title), "Title");
        } catch (java.lang.Exception e) {
            org.iris.client.settings.xml.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "pingservices");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printShort(((short) _Pingservices)), "Pingservices");
        } catch (java.lang.Exception e) {
            org.iris.client.settings.xml.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx2 != len2) {
            context.startElement("", "lookandfeel");
            int idx_6 = idx2;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Lookandfeel.get(idx_6 ++)), "Lookandfeel");
            context.endNamespaceDecls();
            int idx_7 = idx2;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Lookandfeel.get(idx_7 ++)), "Lookandfeel");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Lookandfeel.get(idx2 ++)), "Lookandfeel");
            context.endElement();
        }
        while (idx1 != len1) {
            context.startElement("", "irisserver");
            int idx_8 = idx1;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Irisserver.get(idx_8 ++)), "Irisserver");
            context.endNamespaceDecls();
            int idx_9 = idx1;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Irisserver.get(idx_9 ++)), "Irisserver");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Irisserver.get(idx1 ++)), "Irisserver");
            context.endElement();
        }
    }

    public void serializeAttributes(org.iris.client.settings.xml.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Irisserver == null)? 0 :_Irisserver.size());
        int idx2 = 0;
        final int len2 = ((_Lookandfeel == null)? 0 :_Lookandfeel.size());
        if (!has_Pingservices) {
            context.reportError(com.sun.xml.bind.serializer.Util.createMissingObjectError(this, "Pingservices"));
        }
        while (idx2 != len2) {
            idx2 += 1;
        }
        while (idx1 != len1) {
            idx1 += 1;
        }
    }

    public void serializeURIs(org.iris.client.settings.xml.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Irisserver == null)? 0 :_Irisserver.size());
        int idx2 = 0;
        final int len2 = ((_Lookandfeel == null)? 0 :_Lookandfeel.size());
        if (!has_Pingservices) {
            context.reportError(com.sun.xml.bind.serializer.Util.createMissingObjectError(this, "Pingservices"));
        }
        while (idx2 != len2) {
            idx2 += 1;
        }
        while (idx1 != len1) {
            idx1 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (org.iris.client.settings.xml.IrisclientType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\'com.sun.msv."
+"grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/su"
+"n/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq"
+"\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\tpp\u0000sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000"
+"~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.m"
+"sv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~"
+"\u0000\nxq\u0000~\u0000\u0003q\u0000~\u0000\u0015psr\u00002com.sun.msv.grammar.Expression$AnyStringEx"
+"pression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0014\u0001psr\u0000 com.sun.msv.grammar.Any"
+"NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u001apsr\u0000#com.sun.msv.grammar.SimpleNameClass"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq"
+"\u0000~\u0000!xq\u0000~\u0000\u001ct\u0000,org.iris.client.settings.xml.VersioninfoTypet\u0000+"
+"http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u000fppsq\u0000~\u0000\u0016q\u0000~"
+"\u0000\u0015psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/re"
+"laxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv"
+"/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xsd.QnameT"
+"ype\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\f"
+"namespaceUriq\u0000~\u0000!L\u0000\btypeNameq\u0000~\u0000!L\u0000\nwhiteSpacet\u0000.Lcom/sun/ms"
+"v/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/20"
+"01/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceP"
+"rocessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.Whi"
+"teSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Express"
+"ion$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util"
+".StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000!L\u0000\fnamespaceURIq\u0000~\u0000!x"
+"pq\u0000~\u00002q\u0000~\u00001sq\u0000~\u0000 t\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-"
+"instanceq\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000\u000bversioninfot\u0000\u0000sq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\'p"
+"psr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwa"
+"ysValidxq\u0000~\u0000,q\u0000~\u00001t\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.Whit"
+"eSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00004\u0001q\u0000~\u00007sq\u0000~\u00008q\u0000~\u0000Eq\u0000"
+"~\u00001sq\u0000~\u0000\u000fppsq\u0000~\u0000\u0016q\u0000~\u0000\u0015pq\u0000~\u0000*q\u0000~\u0000:q\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000\u0005titleq\u0000~\u0000?sq\u0000"
+"~\u0000\tpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\'ppsr\u0000\"com.sun.msv.datatype.xsd.ShortType"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1"
+"]\u0090&6k\u00be\u0002\u0000\u0001L\u0000\nbaseFacetst\u0000)Lcom/sun/msv/datatype/xsd/XSDatatyp"
+"eImpl;xq\u0000~\u0000,q\u0000~\u00001t\u0000\u0005shortq\u0000~\u00005sr\u0000*com.sun.msv.datatype.xsd.M"
+"axInclusiveFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.Rang"
+"eFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\nlimitValuet\u0000\u0012Ljava/lang/Object;xr\u00009com.s"
+"un.msv.datatype.xsd.DataTypeWithValueConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT"
+"\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005"
+"Z\u0000\fisFacetFixedZ\u0000\u0012needValueCheckFlagL\u0000\bbaseTypeq\u0000~\u0000RL\u0000\fconcr"
+"eteTypet\u0000\'Lcom/sun/msv/datatype/xsd/ConcreteType;L\u0000\tfacetNam"
+"eq\u0000~\u0000!xq\u0000~\u0000.ppq\u0000~\u00005\u0000\u0001sr\u0000*com.sun.msv.datatype.xsd.MinInclusi"
+"veFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Vppq\u0000~\u00005\u0000\u0000sr\u0000 com.sun.msv.datatype.xs"
+"d.IntType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Qq\u0000~\u00001t\u0000\u0003intq\u0000~\u00005sq\u0000~\u0000Uppq\u0000~\u00005\u0000\u0001sq\u0000"
+"~\u0000\\ppq\u0000~\u00005\u0000\u0000sr\u0000!com.sun.msv.datatype.xsd.LongType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xq\u0000~\u0000Qq\u0000~\u00001t\u0000\u0004longq\u0000~\u00005sq\u0000~\u0000Uppq\u0000~\u00005\u0000\u0001sq\u0000~\u0000\\ppq\u0000~\u00005\u0000\u0000sr\u0000$com"
+".sun.msv.datatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Qq\u0000~\u00001t\u0000\u0007in"
+"tegerq\u0000~\u00005sr\u0000,com.sun.msv.datatype.xsd.FractionDigitsFacet\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.sun.msv.datatype.xsd.DataTypeWithLe"
+"xicalConstraintFacetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000xq\u0000~\u0000Yppq\u0000~\u00005\u0001\u0000sr\u0000#com.sun.ms"
+"v.datatype.xsd.NumberType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000,q\u0000~\u00001t\u0000\u0007decimalq\u0000~"
+"\u00005q\u0000~\u0000ot\u0000\u000efractionDigits\u0000\u0000\u0000\u0000q\u0000~\u0000it\u0000\fminInclusivesr\u0000\u000ejava.lan"
+"g.Long;\u008b\u00e4\u0090\u00cc\u008f#\u00df\u0002\u0000\u0001J\u0000\u0005valuexr\u0000\u0010java.lang.Number\u0086\u00ac\u0095\u001d\u000b\u0094\u00e0\u008b\u0002\u0000\u0000xp\u0080\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000q\u0000~\u0000it\u0000\fmaxInclusivesq\u0000~\u0000s\u007f\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ffq\u0000~\u0000dq\u0000~\u0000rsr\u0000\u0011java.l"
+"ang.Integer\u0012\u00e2\u00a0\u00a4\u00f7\u0081\u00878\u0002\u0000\u0001I\u0000\u0005valuexq\u0000~\u0000t\u0080\u0000\u0000\u0000q\u0000~\u0000dq\u0000~\u0000vsq\u0000~\u0000x\u007f\u00ff\u00ff\u00ff"
+"q\u0000~\u0000_q\u0000~\u0000rsr\u0000\u000fjava.lang.ShorthM7\u00134`\u00daR\u0002\u0000\u0001S\u0000\u0005valuexq\u0000~\u0000t\u0080\u0000q\u0000~\u0000"
+"_q\u0000~\u0000vsq\u0000~\u0000{\u007f\u00ffq\u0000~\u00007sq\u0000~\u00008q\u0000~\u0000Tq\u0000~\u00001sq\u0000~\u0000\u000fppsq\u0000~\u0000\u0016q\u0000~\u0000\u0015pq\u0000~\u0000*"
+"q\u0000~\u0000:q\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000\fpingservicesq\u0000~\u0000?sq\u0000~\u0000\u0011ppsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0000p"
+"psq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u000fppsq\u0000~\u0000\u0011q\u0000~\u0000\u0015psq\u0000~\u0000\u0016q\u0000~\u0000\u0015pq\u0000~\u0000\u0019q\u0000~\u0000\u001dq\u0000~\u0000\u001fsq\u0000"
+"~\u0000 t\u0000,org.iris.client.settings.xml.LookandfeelTypeq\u0000~\u0000$sq\u0000~\u0000"
+"\u000fppsq\u0000~\u0000\u0016q\u0000~\u0000\u0015pq\u0000~\u0000*q\u0000~\u0000:q\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000\u000blookandfeelq\u0000~\u0000?sq\u0000~\u0000"
+"\u0011ppsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u000fppsq\u0000~\u0000\u0011q\u0000~\u0000\u0015psq\u0000~\u0000\u0016q\u0000~\u0000\u0015"
+"pq\u0000~\u0000\u0019q\u0000~\u0000\u001dq\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000+org.iris.client.settings.xml.Irisse"
+"rverTypeq\u0000~\u0000$sq\u0000~\u0000\u000fppsq\u0000~\u0000\u0016q\u0000~\u0000\u0015pq\u0000~\u0000*q\u0000~\u0000:q\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000\niri"
+"sserverq\u0000~\u0000?sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHas"
+"h;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed"
+"\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar"
+"/ExpressionPool;xp\u0000\u0000\u0000\u0016\u0001pq\u0000~\u0000Aq\u0000~\u0000\u0006q\u0000~\u0000\u0013q\u0000~\u0000\u0088q\u0000~\u0000\u0095q\u0000~\u0000%q\u0000~\u0000Iq"
+"\u0000~\u0000\u007fq\u0000~\u0000\u008cq\u0000~\u0000\u0099q\u0000~\u0000\u0005q\u0000~\u0000\bq\u0000~\u0000\u0007q\u0000~\u0000\rq\u0000~\u0000\u0085q\u0000~\u0000\u0092q\u0000~\u0000\u0083q\u0000~\u0000\u0090q\u0000~\u0000Nq"
+"\u0000~\u0000\u0010q\u0000~\u0000\u0087q\u0000~\u0000\u0094x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends org.iris.client.settings.xml.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(org.iris.client.settings.xml.impl.runtime.UnmarshallingContext context) {
            super(context, "----------------");
        }

        protected Unmarshaller(org.iris.client.settings.xml.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return org.iris.client.settings.xml.impl.IrisclientTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  9 :
                        if (("lookandfeel" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 10;
                            return ;
                        }
                        break;
                    case  0 :
                        if (("versioninfo" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        break;
                    case  13 :
                        if (("name" == ___local)&&("" == ___uri)) {
                            _getIrisserver().add(((org.iris.client.settings.xml.impl.IrisserverTypeImpl) spawnChildFromEnterElement((org.iris.client.settings.xml.impl.IrisserverTypeImpl.class), 14, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  15 :
                        if (("irisserver" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 13;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  6 :
                        if (("pingservices" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 7;
                            return ;
                        }
                        break;
                    case  10 :
                        if (("lookandfeelthemepath" == ___local)&&("" == ___uri)) {
                            _getLookandfeel().add(((org.iris.client.settings.xml.impl.LookandfeelTypeImpl) spawnChildFromEnterElement((org.iris.client.settings.xml.impl.LookandfeelTypeImpl.class), 11, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  12 :
                        if (("lookandfeel" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 10;
                            return ;
                        }
                        if (("irisserver" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 13;
                            return ;
                        }
                        break;
                    case  1 :
                        if (("version" == ___local)&&("" == ___uri)) {
                            _Versioninfo = ((org.iris.client.settings.xml.impl.VersioninfoTypeImpl) spawnChildFromEnterElement((org.iris.client.settings.xml.impl.VersioninfoTypeImpl.class), 2, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        break;
                    case  3 :
                        if (("title" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 4;
                            return ;
                        }
                        break;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  14 :
                        if (("irisserver" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 15;
                            return ;
                        }
                        break;
                    case  15 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        if (("title" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                    case  8 :
                        if (("pingservices" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  2 :
                        if (("versioninfo" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  11 :
                        if (("lookandfeel" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 12;
                            return ;
                        }
                        break;
                }
                super.leaveElement(___uri, ___local, ___qname);
                break;
            }
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  15 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.enterAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  15 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.leaveAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void handleText(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                try {
                    switch (state) {
                        case  4 :
                            eatText1(value);
                            state = 5;
                            return ;
                        case  15 :
                            revertToParentFromText(value);
                            return ;
                        case  7 :
                            eatText2(value);
                            state = 8;
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Title = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Pingservices = javax.xml.bind.DatatypeConverter.parseShort(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                has_Pingservices = true;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
