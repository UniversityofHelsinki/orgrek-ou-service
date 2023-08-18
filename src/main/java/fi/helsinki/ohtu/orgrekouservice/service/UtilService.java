package fi.helsinki.ohtu.orgrekouservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import fi.helsinki.ohtu.orgrekouservice.domain.Attribute;
import fi.helsinki.ohtu.orgrekouservice.domain.NodeDTO;
import fi.helsinki.ohtu.orgrekouservice.util.Constants;

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
            String emoLyhenne = "";
            String lyhenne = "";
            String nameFi = "";
            String nameSv = "";
            String nameEn = "";
            for (Attribute attribute : nodeDTO.getAttributes()){
                switch (attribute.getKey()) {
                    case Constants.EMO_LYHENNE:
                        emoLyhenne = attribute.getValue() + ", ";
                        break;
                    case Constants.LYHENNE:
                        lyhenne = " (" + attribute.getValue() + ")";
                        break;
                    case Constants.NAME_FI:
                        nameFi = attribute.getValue();
                        break;
                    case Constants.NAME_SV:
                        nameSv = attribute.getValue();
                        break;
                    case Constants.NAME_EN:
                        nameEn = attribute.getValue();
                        break;
                }
            }
            if(!nameFi.equals("")){
                nodeDTO.setDisplayNameFi(emoLyhenne + nameFi + lyhenne );
            }
            if(!nameSv.equals("")){
                nodeDTO.setDisplayNameSv(emoLyhenne + nameSv + lyhenne );
            }
            if(!nameEn.equals("")){
                nodeDTO.setDisplayNameEn(emoLyhenne + nameEn + lyhenne );
            }
        }
        return nodeDTOS;
    }

    public boolean isThereNameAttributes(List<Attribute> attributes){

        boolean isNameFi = false;
        boolean isNameSv = false;
        boolean isNameEn = false;

        for(Attribute attribute : attributes){
            if(attribute.getKey().equals(Constants.NAME_FI)){
                isNameFi = true;
                break;
            }
            if(attribute.getKey().equals(Constants.NAME_SV)){
                isNameSv = true;
                break;
            }
            if(attribute.getKey().equals(Constants.NAME_EN)){
                isNameEn = true;
                break;
            }
        }
        return isNameFi || isNameEn || isNameSv;
    }
}
