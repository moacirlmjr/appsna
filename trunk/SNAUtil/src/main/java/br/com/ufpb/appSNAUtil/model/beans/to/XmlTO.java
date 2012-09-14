package br.com.ufpb.appSNAUtil.model.beans.to;

public class XmlTO {
	
	private String attrId;
	private boolean isForNode;
	private String attrName;
	private String attrType;
	
	public XmlTO(){
		
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public boolean isForNode() {
		return isForNode;
	}

	public void setForNode(boolean isForNode) {
		this.isForNode = isForNode;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	
	
	
}
