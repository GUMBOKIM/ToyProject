import React from "react";
import {useParams} from "react-router-dom";
import CardItemList from "../common/card/CardItemList";

const BoardPage: React.FC = () => {
    const {boardName} = useParams();
    return (
        <>
            <CardItemList/>
            <div>Hello BoardPage : {boardName}</div>
        </>
    );
}

export default BoardPage;