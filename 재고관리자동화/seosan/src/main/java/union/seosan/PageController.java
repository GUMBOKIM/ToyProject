package union.seosan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public ResponseEntity home(){
        return new ResponseEntity("정원겸 병신", HttpStatus.OK);
    }

    @RequestMapping("/barcode")
    public String barcodePage() {

        return "task/barcode";
    }
}
