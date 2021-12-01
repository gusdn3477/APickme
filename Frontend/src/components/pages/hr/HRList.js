import React, { useState, useEffect } from 'react';
import Banner from '../../elements/ui/Banner';
import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import HRListForm from './HRListForm';
import Pagination from '../../../utilities/Pagination';
import { paginate } from '../../../utilities/paginate';

export default function HRList() {

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [jobpage, setJobpage] = useState({
    jobdata: [],
    pageSize: 8,
    currentPage: 1,
  });

  useEffect(() => {
    fetch(`/hr-service/hr/${localStorage.getItem('empNo')}`)
      .then(res => {
        return res.json();
      })
      .then(data => {
        setData(data);
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

  if (loading) return <div class="spinner-border text-primary" role="status"></div>;
  return (
    <div id="wrap">
      <Header />
      <div className="container-scroller">
        <div className="container-fluid page-body-wrapper">
          {/* Banner는 왼쪽에 있는 리스트 형식의 메뉴 */}
          <Banner />
          {/* 여기부터 프사 누르면 나오는 메뉴 */}
          <div className="main-panel">
            <div className="content-wrapper">
              <div className="row">
                <div className="col-md-12 grid-margin stretch-card">
                  <div className="card">
                    <div className="card-body2">
                      <p className="card-title mb-3 ">인사담당자 명단</p>
                      <div className="table-responsive">
                        <table className="table table-striped table-borderless">
                          <thead>
                            <tr>
                              <th>번호</th>
                              <th>이름</th>
                              <th>이메일</th>
                              <th>인사담당자 코드</th>
                              <th>직급</th>
                              <th style={{textAlign:"center"}}>관리자 허가</th>
                              <th style={{textAlign:"center"}}>삭제하기</th>
                            </tr>
                          </thead>
                          <tbody>
                          {
                              pagedJobs.length > 0 && pagedJobs.map(
                                (data, idx) => (
                                  <HRListForm
                                    idx={idx+1}
                                    key={data.idx}
                                    data={data}
                                    setMyList={setData}
                                  />
                                )
                                )
                            }
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div >
              <Pagination
                itemsCount={data.length}
                pageSize={pageSize}
                currentPage={currentPage}
                onPageChange={handlePageChange}
              /></div>
            </div>
            <Footer />
          </div>
        </div>
      </div>
    </div>

  );
}