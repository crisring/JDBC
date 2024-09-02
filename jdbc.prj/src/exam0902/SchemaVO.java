package exam0902;

public class SchemaVO {

	private String columnName, columnLableName, isNuallalbe;
	private int dataSize;

	public SchemaVO() {

	}// SchemaVO

	public SchemaVO(String columnName, String columnLableName, String isNuallalbe, int dataSize) {
		super();
		this.columnName = columnName;
		this.columnLableName = columnLableName;
		this.isNuallalbe = isNuallalbe;
		this.dataSize = dataSize;
	}// SchemaVO

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnLableName() {
		return columnLableName;
	}

	public void setColumnLableName(String columnLableName) {
		this.columnLableName = columnLableName;
	}

	public String getIsNuallalbe() {
		return isNuallalbe;
	}

	public void setIsNuallalbe(String isNuallalbe) {
		this.isNuallalbe = isNuallalbe;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

}
