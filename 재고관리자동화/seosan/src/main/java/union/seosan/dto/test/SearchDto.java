package union.seosan.dto.test;

import lombok.Data;

import java.util.HashMap;

@Data
public class SearchDto {

    private int currPageNo = 0;
    private String jqGridPaging = "Y";
    private HashMap<String, Object> map = new HashMap<>();
    private int userPageUnit = 100;

    public SearchDto() {
        map.put("funcType", "search");
        map.put("searchLineCd", "");
        map.put("searchPartCd", "");
        map.put("searchplnSMon", "202202");
        HashMap<String, Object> searchPlantCd = new HashMap<>();
        HashMap<String, Object> temp = new HashMap<>();
        temp.put("index", 0);
        temp.put("item", 1105);
        searchPlantCd.put("0",temp);
        map.put("searchPlantCd", searchPlantCd);
    }
}
