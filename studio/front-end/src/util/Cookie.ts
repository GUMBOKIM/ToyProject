import {Cookies} from "react-cookie";

const cookie = new Cookies();

export const GetCookie = (name : string ) => {
    return cookie.get(name);
}

export const SetCookie = (name : string, value: any ) => {
    return cookie.set(name, value);
}