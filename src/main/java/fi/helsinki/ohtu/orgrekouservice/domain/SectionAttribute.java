package fi.helsinki.ohtu.orgrekouservice.domain;
import java.util.Date;

public class SectionAttribute {
    private Integer id;
    private String section;
    private String attr;
    private Date startDate;
    private Date endDate;

    private int orderNro;

    public SectionAttribute() {
    }

    public SectionAttribute(Integer id, String section, String attr, Date startDate, Date endDate, int orderNro) {
        this.id = id;
        this.section = section;
        this.attr = attr;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderNro = orderNro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getOrderNro() {
        return orderNro;
    }

    public void setOrderNro(int orderNro) {
        this.orderNro = orderNro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionAttribute that = (SectionAttribute) o;

        if (!getSection().equals(that.getSection())) return false;
        return getAttr().equals(that.getAttr());
    }

    @Override
    public int hashCode() {
        int result = getSection().hashCode();
        result = 31 * result + getAttr().hashCode();
        return result;
    }
}

