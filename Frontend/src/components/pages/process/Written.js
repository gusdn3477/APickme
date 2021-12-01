import React, { useState } from "react";
import Banner from "../../elements/ui/Banner";
import Header from "../../layout/Header";
import Footer from "../../layout/Footer";
import { useEffect } from "react";
import HRCard from "../../elements/widgets/Home/HRCard";
import Pagination from "../../../utilities/Pagination";
import { paginate } from "../../../utilities/paginate";

export default function Written() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [jobpage, setJobpage] = useState({
    jobdata: [],
    pageSize: 4,
    currentPage: 1,
  });

  useEffect(() => {
    fetch(`/job-service/${localStorage.getItem("corpNo")}/jobs/closed`)
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        setData(data);
        console.log(data);
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
  console.log(pagedJobs);

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
              <div
                class="alert alert-primary d-flex align-items-center"
                role="alert"
                style={{ width: "98%" }}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  fill="currentColor"
                  class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2"
                  viewBox="0 0 16 16"
                  role="img"
                  aria-label="Warning:"
                >
                  <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                </svg>
                <div>필기 전형 - 마감된 공고만 보입니다.</div>
              </div>

              <div className="row row-cols-2">
                {pagedJobs.map((data) => (
                  // 아래는 마감된 공고
                  <HRCard
                    what={"written"}
                    // idx={idx + 1}
                    // key={item.idx}
                    data={data}
                    setData={setData}
                  />
                ))}
              </div>
              <div>
                <Pagination
                  itemsCount={data.length}
                  pageSize={pageSize}
                  currentPage={currentPage}
                  onPageChange={handlePageChange}
                />
              </div>
            </div>
            <Footer />
          </div>
        </div>
      </div>
    </div>
  );
}
