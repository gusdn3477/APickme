import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

// 마감된 공고만 보여주는 페이지입니다. (마감되지 않은 공고를 채점하면 안되므로)
export default function FinalProcessCard({ idx, key, what, data, setData }) {

  if (data.closed === 'F') return "";
  return (
    <div className="card" style={{ width: "60rem", margin: "13px" }}>
      <div className="card-body">
        <h5 className="card-title">{data.jobsTitle}</h5>
        <h6 className="card-description"><i class="fas fa-angle-right"></i>&nbsp;&nbsp;{data.jobsContext}</h6>
        <p className="card-text card-text-ing">고용형태 :  {data.jobType}</p>
        <p className="card-text card-text-ing">채용유형 :  {data.jobQualify}</p>
        <p className="card-text card-text-ing">지원자격 :  {data.employType}</p>
        {data.applyStart && data.applyEnd ?
          <p className="card-text  card-text-ing">지원기간 : {(data.applyStart).substring(0, 10)} ~ {(data.applyEnd).substring(0, 10)}</p> : ""
        }
        <Link to={`/process/${what}/${data.jobsNo}`}>
          <button type="button" className="btn btn-primary" style={{ margin: "0.8rem" }}>명단 보기</button>
        </Link>
      </div>
    </div>
  )
};
