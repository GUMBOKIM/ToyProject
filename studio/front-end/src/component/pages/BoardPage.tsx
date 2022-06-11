import React from "react";
import {useParams} from "react-router-dom";

const BoardPage: React.FC = () => {
    const {boardName} = useParams();
    return <div>Hello BoardPage : {boardName}</div>
}

export default BoardPage;