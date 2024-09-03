package kr.co.sist.vo;

public class SchemaVO {
	private String columnName, columnTypeName, isNullable;
	private int dataSize;

	public SchemaVO() {

	}

	public SchemaVO(String columnName, String columnTypeName, String isNullable, int dataSize) {
		this.columnName = columnName;
		this.columnTypeName = columnTypeName;
		this.isNullable = isNullable;
		this.dataSize = dataSize;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	@Override
	public String toString() {
		return "SchemaVO [columnName=" + columnName + ", columnTypeName=" + columnTypeName + ", isNullable="
				+ isNullable + ", dataSize=" + dataSize + "]";
	}

}
