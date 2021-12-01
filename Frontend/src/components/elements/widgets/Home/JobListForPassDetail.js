import React, { useState, useEffect } from "react";
import Banner from "../../ui/Banner";
import Header from "../../../layout/Header";
import Footer from "../../../layout/Footer";
import JobListForProcessDetailForm from "../../widgets/Form/JobListForProcessDetailForm";
import { useParams } from "react-router";

export default function JobListForPassDetail() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const { jobsNo } = useParams();
  const [jobTitle, setJobTitle] = useState([]);
  const [data2, setData2] =useState([]);

  
  useEffect(() => {
    fetch(`/process-service/process/final/${jobsNo}`)
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        setData(data);
        setData2(data.filter(data => data.secondInterviewResult === 'P'));
        setLoading(false);
        console.log('최종 확인용', data2);
        setData(data2)
      });

    fetch(`/job-service/jobs/${jobsNo}`)
      .then((res) => {
        return res.json();
      })
      .then((jobTitle) => {
        setJobTitle(jobTitle);
        setLoading(false);
        console.log(jobTitle);
      });

  }, []);

  if (loading)
    return (
      <div class="spinner-border text-primary" role="status">
      </div>
    );
  return (
    <div id="wrap">
      <Header />
      <div className="container-scroller">
        <div className="container-fluid page-body-wrapper">
          <Banner />
          <div className="main-panel">
            <div className="content-wrapper">
              <div className="row">
                <div className="col-md-12 grid-margin stretch-card">
                  <div className="card">
                    <div className="card-body">
                      <p className="card-title mb-4 ml-3">{jobTitle.jobsTitle} 합격자 명단 </p>
                      <div className="table-responsive">
                        <table className="table table-striped table-borderless" style={{marginBottom:"2rem"}}>
                          <thead>
                            <tr>
                              <th>번호</th>
                              <th>지원자</th>
                              <th>이메일</th>
                              <th>전화번호</th>
                              <th>필기 전형 점수</th>
                              <th>1차 면접 점수</th>
                              <th>2차 면접 점수</th>
                            </tr>
                          </thead>
                          <tbody>
                            {data2.map((item, idx) => (
                                <JobListForProcessDetailForm
                                  idx={idx + 1}
                                  key={item.id}
                                  data={item}
                                  setData={setData}
                                />
                              ))}
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <Footer />
          </div>
        </div>
      </div>
    </div>
  );
}
