import React, { useState, useEffect } from 'react';
import Banner from '../../elements/ui/Banner';
import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import IntervieweeForm from '../../elements/widgets/Form/IntervieweeListForm';
// import HRListForm from './IntervieweeListForm';

export default function IntervieweeList() {

  const [data, setData] = useState([]);
  useEffect(() => {
    fetch(`/user-service/apply/${localStorage.getItem('userId')}`)
      .then(res => {
        return res.json();
      })
      .then(data => {
        setData(data);
      });
  }, []);

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
                    <div className="card-body">
                      <p className="card-title mb-0">면접자 명단</p>
                      <div className="table-responsive">
                        <table className="table table-striped table-borderless">
                          <thead>
                            <tr>
                              <th>이름</th>
                              <th>지원자 번호</th>
                              <th>필기 점수</th>
                              <th>1차 면접 시간</th>
                              <th>1차 면접 담당자</th>
                              <th>1차 면접 점수</th>
                              <th>1차 면접 결과</th>
                              <th>2차 면접 시간</th>
                              <th>2차 면접 담당자</th>
                              <th>2차 면접 점수</th>
                              <th>2차 면접 결과</th>
                            </tr>
                          </thead>
                          <tbody>
                            {
                              data.length > 0 && data.map(
                                (item => {
                                  <IntervieweeForm
                                    key={item.id}
                                    data={item}
                                    setMyList={setData}
                                  />
                                })
                              )
                            }
                            <tr>
                              <td>Search Engine Marketing</td>
                              <td className="font-weight-bold">$362</td>
                              <td>21 Sep 2018</td>
                              <td className="font-weight-medium"><div className="badge badge-success">Completed</div></td>
                            </tr>
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