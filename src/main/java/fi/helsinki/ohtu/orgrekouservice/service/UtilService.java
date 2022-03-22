package fi.helsinki.ohtu.orgrekouservice.service;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeDTO;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UtilService {

    public String parseDateFromDatabase(String date) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date parsedDate = parser.parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(parsedDate);
    }

    public List<NodeDTO> getDisplayNames(List<NodeDTO> nodeDTOS){
        for (NodeDTO nodeDTO : nodeDTOS){
            String emo_lyhenne = "";
            String lyhenne = "";
            String name_fi = "";
            String name_sv = "";
            String name_en = "";
            for (Attribute attribute : nodeDTO.getAttributes()){
                switch (attribute.getKey()) {
                    case Constants.EMO_LYHENNE:
                        emo_lyhenne = attribute.getValue() + ", ";
                        break;
                    case Constants.LYHENNE:
                        lyhenne = " (" + attribute.getValue() + ")";
                        break;
                    case Constants.NAME_FI:
                        name_fi = attribute.getValue();
                        break;
                    case Constants.NAME_SV:
                        name_sv = attribute.getValue();
                        break;
                    case Constants.NAME_EN:
                        name_en = attribute.getValue();
                        break;
                }
            }
            nodeDTO.setDisplayNameFi(emo_lyhenne + name_fi + lyhenne );
            nodeDTO.setDisplayNameSv(emo_lyhenne + name_sv + lyhenne );
            nodeDTO.setDisplayNameEn(emo_lyhenne + name_en + lyhenne );
        }
        return nodeDTOS;
    }

    public boolean isThereNameAttributes(List<Attribute> attributes){

        boolean isNameFi = false;
        boolean isNameSv = false;
        boolean isNameEn = false;

        for(Attribute attribute : attributes){
            switch (attribute.getKey()) {
                case Constants.NAME_FI:
                    isNameFi = true;
                    break;
                case Constants.NAME_SV:
                    isNameSv = true;
                    break;
                case Constants.NAME_EN:
                    isNameEn = true;
                    break;
            }
        }
        return isNameEn && isNameFi && isNameSv;
    }
}
