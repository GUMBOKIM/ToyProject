import {Injectable} from '@nestjs/common';
import {HttpService} from "@nestjs/axios";
import {map} from "rxjs";

@Injectable()
export class TaskDeliveryService {
    constructor(private httpService: HttpService) {
    }

    async getProductPlan(){
        const response = await this.httpService.get("https://es-qms.borgwarner.com/qms/kqis9210__tt.query_data?p_companycd=00&p_x=1&p_plant=&p_produce_line=&p_workcenter=&p_part_no=",
            {
                headers: {
                    'authority': "es-qms.borgwarner.com",
                    'method': "GET",
                    'path': "/qms/kqis9210__tt.query_data?p_companycd=00&p_x=1&p_plant=&p_produce_line=&p_workcenter=&p_part_no=",
                    'accept': "*/*",
                    'accept-encoding': "gzip, deflate, br",
                    'accept-language': "en,ko;q=0.9,fr;q=0.8",
                    'cookie': "SYSLANG=KO; SYSTYPE=1; SYSCOMP=00; SYSID=106076; CCODE=106076",
                    'referer': "https://es-qms.borgwarner.com/qms/kqis9210__tt.query?p_companycd=00&p_x=1&p_plant=&p_produce_line=&p_workcenter=&p_part_no=",
                    'sec-ch-ua': '"Chromium";v="94", "Google Chrome";v="94", ";Not A Brand";v="99:',
                    'sec-ch-ua-mobile': "?0",
                    'sec-ch-ua-platform': '"Windows"',
                    'sec-fetch-dest': "empty",
                    'sec-fetch-mode': "cors",
                    'sec-fetch-site': "same-origin",
                    'user-agent': "XMLHttpRequest",
                }

            }
        );
        let data = response.data;
        return ;
    }
}
