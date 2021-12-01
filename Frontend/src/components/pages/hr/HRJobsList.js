import React, { useState } from "react";
import Banner from "../../elements/ui/Banner";
import Header from "../../layout/Header";
import Footer from "../../layout/Footer";
import { useEffect } from "react";
import Pagination from "../../../utilities/Pagination";
import { paginate } from "../../../utilities/paginate";
import { Link } from "react-router-dom";

// Jobs list로 보시면 됩니다.
export default function HRJobsList() {
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
        setLoading(false);
      });
  }, []);

  const handlePageChange = (page) => {
    setLoading(true);
    setJobpage({ ...jobpage, currentPage: page });
    setLoading(false);
  };

  const { jobdata, pageSize, currentPage } = jobpage;
  const pagedJobs = paginate(data, currentPage, pageSize);

  if (loading)
    return <div class="spinner-border text-primary" role="status"></div>;
  return (
    <div id="wrap">
      <Header />
      <div className="container-scroller">
        <div className="container-fluid page-body-wrapper">
          <Banner />
          <div className="main-panel">
            <div className="content-wrapper">
              <div className="row">
              {pagedJobs.map((data) => (
                  // <Card  data={job} setData={job} />
                  <div className="card" style={{ width: "70%" , margin: "25px", marginLeft: "100px" }}>
                    <div>
                      {data.closed === "T" ? (
                        <div className="card-inverse-info card-inverse-info-position ">
                          <i class="fas fa-exclamation"></i>&nbsp;&nbsp;마감된
                          공고입니다.
                        </div>
                      ) : (
                        ""
                      )}
                    </div>
                    <div className="card-body">
                    {(data.closed === "T") ?
                        <><h5 className="card-title" style={{ color: "#949aa1" }}>{data.jobsTitle} </h5>
                        <h6 className="card-description">{data.jobsContext}</h6>
                        <p className="card-text card-text-end">고용형태 : {data.jobType}</p>
                      <p className="card-text card-text-end">채용유형 : {data.jobQualify}</p>
                      <p className="card-text card-text-end">지원자격 : {data.employType}</p>
                      {data.applyStart && data.applyEnd ? (
                        <p
                          className="card-text"
                          style={{ color: "red", marginLeft: ".5rem" }}
                        >
                          지원기간 : {data.applyStart.substring(0, 10)} ~{" "}
                          {data.applyEnd.substring(0, 10)}
                        </p>
                      ) : ("")
                      }</>
                         : <><h5 className="card-title" >{data.jobsTitle} </h5>
                         <h6 className="card-description">{data.jobsContext}</h6>
                      <p className="card-text card-text-ing">고용형태 : {data.jobType}</p>
                      <p className="card-text card-text-ing">채용유형 : {data.jobQualify}</p>
                      <p className="card-text card-text-ing">지원자격 : {data.employType}</p>
                      {data.applyStart && data.applyEnd ? (
                        <p className="card-text"
                          style={{ color: "red", marginLeft: ".5rem" }}>
                          지원기간 : {data.applyStart.substring(0, 10)} ~{" "}
                          {data.applyEnd.substring(0, 10)}
                        </p>
                      ) : ("")
                      }
                         </>}
                      <Link to={`/jobs/${data.jobsNo}`}>
                        <button
                          type="button"
                          className="btn btn-primary"
                          style={{
                            marginTop: "0.8rem",
                            backgroundColor: "#4b49acbf",
                          }}
                        >
                          공고보기
                        </button>
                      </Link>
                    </div>
                  </div>
                ))}
                <div>
                  <Pagination
                    itemsCount={data.length}
                    pageSize={pageSize}
                    currentPage={currentPage}
                    onPageChange={handlePageChange}
                  /></div>
              </div>
            </div>
            <Footer />
          </div>
        </div>
      </div>
    </div>
  );
}
