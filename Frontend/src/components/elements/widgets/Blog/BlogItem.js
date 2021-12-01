import React, { useEffect, useState } from 'react';
import {Link} from 'react-router-dom';


export default function BlogItem(){

    const blog = [{
        "id": 1,
        "image": "/assets/img/blog/01.jpg",
        "category": [
          "lifestyle",
          "men"
        ],
        "title": "책 없는 방은 영혼 없는 육체와 같다.",
        "url": "/blog-details-standard",
        "author": "-키케로-",
        "authorUrl": "/blog-standard"
      },
      {
        "id": 2,
        "image": "/assets/img/blog/02.jpg",
        "category": [
          "lifestyle"
        ],
        "title": "가장 훌륭한 벗은 가장 좋은 책이다.",
        "url": "/blog-details-standard",
        "author": "-체스터필드-",
        "authorUrl": "/blog-standard"
      },
      {
        "id": 3,
        "image": "/assets/img/blog/03.jpg",
        "category": [
          "lifestyle"
        ],
        "title": "집은 책으로, 정원은 꽃으로 가득 채워라.",
        "url": "/blog-details-standard",
        "author": "-엔듀르 랑그-",
        "authorUrl": "/blog-standard"
      }
    ];

    const blogList = blog.map(item => (
    
    <div className="col-12 col-md-4 mb-4">
        
        <Link to = {`/productlist` } >
        <div className="blogImg" style ={{backgroundImage : `url(${item.image})`, backgroundSize:"cover"}}></div>     
        </Link>


        <div className="blogTxt">
           <Link to ={`/productlist` } ><p className="blogTitle">{item.title}</p></Link>
            <Link to = {`/productlist`}><p className="blogAuth">{item.author}</p></Link>
        </div>

    </div>
    )).slice(0,3);


    return(
        <div className = "row mt-5">
            {blogList}
        </div>
    );

}