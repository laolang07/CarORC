package com.wintone.plateid;

/**
 * 车牌识别结果
 * @author PAUL
 * @email foolstudio@qq.com
 * @since 2015.6
 */
public class TH_PlateIDResult
{

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("颜色：");sb.append(color);sb.append("　");
        sb.append("号码：");sb.append(license);

        return (sb.toString() );
    }
    public TH_PlateIDResult()
    {
        license = "";
        color = "";
        pbyBits = "";
        nCarBright = 0;
        nCarColor = 0;
        reserved = "";
    }

    int bottom;
    String color;
    int left;
    String license;
    int nBright;
    int nCarBright;
    int nCarColor;
    int nColor;
    int nConfidence;
    int nDirection;
    int nTime;
    int nType;
    String pbyBits;
    String reserved;
    int right;
    int top;

    public int getBottom() {
        return bottom;
    }
    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public int getLeft() {
        return left;
    }
    public void setLeft(int left) {
        this.left = left;
    }

    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license;
    }

    public int getnBright() {
        return nBright;
    }
    public void setnBright(int nBright) {
        this.nBright = nBright;
    }

    public int getnCarBright() {
        return nCarBright;
    }
    public void setnCarBright(int nCarBright) {
        this.nCarBright = nCarBright;
    }

    public int getnCarColor() {
        return nCarColor;
    }
    public void setnCarColor(int nCarColor) {
        this.nCarColor = nCarColor;
    }

    public int getnColor() {
        return nColor;
    }
    public void setnColor(int nColor) {
        this.nColor = nColor;
    }

    public int getnConfidence() {
        return nConfidence;
    }
    public void setnConfidence(int nConfidence) {
        this.nConfidence = nConfidence;
    }

    public int getnDirection() {
        return nDirection;
    }
    public void setnDirection(int nDirection) {
        this.nDirection = nDirection;
    }

    public int getnTime() {
        return nTime;
    }
    public void setnTime(int nTime) {
        this.nTime = nTime;
    }

    public int getnType() {
        return nType;
    }
    public void setnType(int nType) {
        this.nType = nType;
    }

    public String getPbyBits() {
        return pbyBits;
    }
    public void setPbyBits(String pbyBits) {
        this.pbyBits = pbyBits;
    }

    public String getReserved() {
        return reserved;
    }
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public int getRight() {
        return right;
    }
    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }
    public void setTop(int top) {
        this.top = top;
    }
}
