//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.27 at 10:52:14 CEST 
//


package org.iris.xml.server.settings.impl;

public class SortonresendImpl implements org.iris.xml.server.settings.Sortonresend, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, org.iris.xml.server.settings.impl.runtime.UnmarshallableObject, org.iris.xml.server.settings.impl.runtime.XMLSerializable, org.iris.xml.server.settings.impl.runtime.ValidatableObject
{

    protected boolean has_Value;
    protected boolean _Value;
    public final static java.lang.Class version = (org.iris.xml.server.settings.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    public SortonresendImpl() {
    }

    public SortonresendImpl(boolean value) {
        _Value = value;
        has_Value = true;
    }

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (org.iris.xml.server.settings.Sortonresend.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "sortonresend";
    }

    public boolean isValue() {
        return _Value;
    }

    public void setValue(boolean value) {
        _Value = value;
        has_Value = true;
    }

    public org.iris.xml.server.settings.impl.runtime.UnmarshallingEventHandler createUnmarshaller(org.iris.xml.server.settings.impl.runtime.UnmarshallingContext context) {
        return new org.iris.xml.server.settings.impl.SortonresendImpl.Unmarshaller(context);
    }

    public void serializeBody(org.iris.xml.server.settings.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if (!has_Value) {
            context.reportError(com.sun.xml.bind.serializer.Util.createMissingObjectError(this, "Value"));
        }
        context.startElement("", "sortonresend");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(javax.xml.bind.DatatypeConverter.printBoolean(((boolean) _Value)), "Value");
        } catch (java.lang.Exception e) {
            org.iris.xml.server.settings.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
    }

    public void serializeAttributes(org.iris.xml.server.settings.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if (!has_Value) {
            context.reportError(com.sun.xml.bind.serializer.Util.createMissingObjectError(this, "Value"));
        }
    }

    public void serializeURIs(org.iris.xml.server.settings.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if (!has_Value) {
            context.reportError(com.sun.xml.bind.serializer.Util.createMissingObjectError(this, "Value"));
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (org.iris.xml.server.settings.Sortonresend.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
+"grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
+"\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
+"msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
+"ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000\u001fcom.sun.msv.gra"
+"mmar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.g"
+"rammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datat"
+"ype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000"
+"~\u0000\u0004ppsr\u0000$com.sun.msv.datatype.xsd.BooleanType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*"
+"com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com"
+".sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv"
+".datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Lja"
+"va/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0012L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/"
+"datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001"
+"/XMLSchemat\u0000\u0007booleansr\u00005com.sun.msv.datatype.xsd.WhiteSpaceP"
+"rocessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.Whi"
+"teSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Express"
+"ion$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.util"
+".StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0012L\u0000\fnamespaceURIq\u0000~\u0000\u0012x"
+"pq\u0000~\u0000\u0016q\u0000~\u0000\u0015sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000"
+"\bppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000"
+"\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005v"
+"aluexp\u0000psq\u0000~\u0000\nppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u000fq\u0000~\u0000\u0015t\u0000\u0005QNameq\u0000~\u0000\u0019q\u0000~\u0000\u001bsq\u0000~\u0000\u001cq\u0000~\u0000\'q\u0000~\u0000\u0015sr\u0000#com.su"
+"n.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0012L\u0000\f"
+"namespaceURIq\u0000~\u0000\u0012xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-instancesr\u00000co"
+"m.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000"
+"~\u0000\u0004sq\u0000~\u0000\"\u0001psq\u0000~\u0000)t\u0000\fsortonresendt\u0000\u0000sr\u0000\"com.sun.msv.grammar.E"
+"xpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/E"
+"xpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.Expressio"
+"nPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parent"
+"t\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0002\u0001pq\u0000~\u0000\tq\u0000~\u0000\u001fx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends org.iris.xml.server.settings.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(org.iris.xml.server.settings.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(org.iris.xml.server.settings.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return org.iris.xml.server.settings.impl.SortonresendImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  0 :
                        if (("sortonresend" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
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
                    case  2 :
                        if (("sortonresend" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
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
                    case  3 :
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
                    case  3 :
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
                        case  1 :
                            eatText1(value);
                            state = 2;
                            return ;
                        case  3 :
                            revertToParentFromText(value);
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
                _Value = javax.xml.bind.DatatypeConverter.parseBoolean(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                has_Value = true;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
