package fi.helsinki.ohtu.orgrekouservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Edge {
        private Integer id;
        private String parentNodeId;
        private int childUniqueId;
        private Date startDate;
        private Date endDate;
        private String hierarchy;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getParentNodeId() {
            return parentNodeId;
        }

        public void setParentNodeId(String parentNodeId) {
            this.parentNodeId = parentNodeId;
        }

        public int getChildUniqueId() {
            return childUniqueId;
        }

        public void setChildUniqueId(int childUniqueId) {
            this.childUniqueId = childUniqueId;
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

        public String getHierarchy() {
            return hierarchy;
        }

        public void setHierarchy(String hierarchy) {
            this.hierarchy = hierarchy;
        }
}
