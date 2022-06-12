import React from "react";
import {useParams} from "react-router-dom";

const PostPage: React.FC = () => {
    const {postId} = useParams();
    return <div>Hello PostPage Post Id {postId}</div>
}

export default PostPage;