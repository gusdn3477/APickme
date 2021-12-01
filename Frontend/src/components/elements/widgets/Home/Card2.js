import React, { useState } from "react";
import { useEffect } from "react";
import { paginate } from "../../../utilities/paginate";
import { Link } from "react-router-dom";

// Jobs list로 보시면 됩니다.
export default function Card2({ idx, key, data, setData }) {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [jobpage, setJobpage] = useState({
    jobdata: [],
    pageSize: 3,
    currentPage: 1,
  });

  useEffect(() => {
    fetch(`/job-service/${localStorage.getItem("corpNo")}/jobs`)
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        setData(data);
        // console.log(data);
        setLoading(false);
      });
  }, []);

  const handlePageChange = (page) => {
    setLoading(true)
    setJobpage({ ...jobpage, currentPage: page });
    setLoading(false)
  };

  const { jobdata, pageSize, currentPage } = jobpage;
  const pagedJobs = paginate(data, currentPage, pageSize);
  console.log(pagedJobs);

  if (data.closed === "T") {
    return (
      <div className="card" style={{ width: "60rem", margin: "13px" }}>

        <div className="card-inverse-info card-inverse-info-position ">
          <i class="fas fa-exclamation"></i>&nbsp;&nbsp;마감된 공고입니다.
        </div>
        <div className="card-body">
          <h5 className="card-title" style={{ color: "#949aa1" }}>{data.jobsTitle}</h5>
          <h6 className="card-description">{data.jobsContext}</h6>
          <p className="card-text card-text-end">고용형태 : {data.jobType}</p>
          <p className="card-text card-text-end">채용유형 : {data.jobQualify}</p>
          <p className="card-text card-text-end">지원자격 : {data.employType}</p>
          {data.applyStart && data.applyEnd ? (
            <p className="card-text"
              style={{ color: "red", marginLeft: ".5rem" }}>
              지원기간 : {data.applyStart.substring(0, 10)} ~{" "}
              {data.applyEnd.substring(0, 10)}
            </p>
          ) : ("")
          }
          <Link to={`/jobs/${data.jobsNo}`}>
            <button
              type="button"
              className="btn btn-primary"
              style={{ marginTop: "0.8rem", backgroundColor: "#4b49acbf" }}
            >
              공고보기
            </button>
          </Link>
        </div>
      </div>
    )
  }
  else {
    return (
      <div className="card card-outline-success" style={{ width: "60rem", margin: "13px" }}>
        <div className="card-body">
          <h5 className="card-title">{data.jobsTitle}</h5>
          <h6 className="card-description">{data.jobsContext}</h6>
          <p className="card-text card-text-ing">고용형태 : {data.jobType}</p>
          <p className="card-text card-text-ing">채용유형 : {data.jobQualify}</p>
          <p className="card-text card-text-ing">지원자격 : {data.employType}</p>
          {data.applyStart && data.applyEnd ? (
            <p className="card-text card-text-ing">
              지원기간 : {data.applyStart.substring(0, 10)} ~{" "}
              {data.applyEnd.substring(0, 10)}
            </p>
          ) : (
            ""
          )}
          <Link to={`/jobs/${data?.jobsNo}`}>
            <button
              type="button"
              className="btn btn-primary"
              style={{ marginTop: "0.8rem" }}
            >
              공고보기
            </button>
          </Link>
        </div>
      </div>
    );
  }
}

