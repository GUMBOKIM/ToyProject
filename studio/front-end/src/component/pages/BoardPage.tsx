import React from "react";
import {useParams} from "react-router-dom";
import CardItemList from "../common/card/CardItemList";

const BoardPage: React.FC = () => {
    const {boardId} = useParams();
    return (
        <>
            <CardItemList/>
            <div>Hello BoardPage : {boardId}</div>
        </>
    );
}

export default BoardPage;