import React, { useState, useEffect } from 'react';
import Banner from '../../elements/ui/Banner';
import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import { useParams } from 'react-router';
import SecondInterviewTableForm from '../../elements/widgets/Form/SecondInterviewTableForm';
import Pagination from '../../../utilities/Pagination';
import { paginate } from '../../../utilities/paginate';
import ChangeJobString from '../../../utilities/ChangeString';

export default function SecondInterviewScore() {

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [secondInterviewPass, setSecondInterviewPass] = useState();
  const [save, setSave] = useState();
  const { jobsNo } = useParams();
  const [jobpage, setJobpage] = useState({
    jobdata: [],
    pageSize: 8,
    currentPage: 1,
  });

  useEffect(() => {
    fetch(`/process-service/process/second-interview/${jobsNo}`)
      .then(res => {
        return res.json();
      })
      .then(data => {
        const data2 = data.filter(data => data.firstInterviewResult === 'P');
        return data2;
      })
      .then(res => {
        setData(res);
        console.log('data2', res);
      }
      )
      .then(
        fetch(`/job-service/jobprocess/${jobsNo}`) // 2차 합격 인원 가져오기
          .then(res => {
            return res.json();
          })
          .then(data => {
            setSecondInterviewPass(data);
            console.log(data);
            setLoading(false);
          })
      );
  }, []);

  const PassOrNot = () => { // jobprocess 가져올 수 있어야 함

    if (!data[0]) {
      alert("합/불 여부를 결정할 명단이 없습니다.");
      return;
    }
    if (data[0].secondCheck) {
      alert("이미 합/불 여부가 결정났습니다.");
      return;
    }
    setLoading(true);
    fetch(`/process-service/process/second-interview/result`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        jobsNo: jobsNo,
        secondInterviewer: localStorage.getItem('empNo'),
        count: secondInterviewPass.intv2Pass
      }),
    }).then(res => res.json())
      .then(
        res => {
          setData(res);
        }
      )
      .then(res => {
        console.log('res 확인용', data);
        console.log('second 확인용', save);
        fetch(`/process-service/process/second-interview/${jobsNo}`)
          .then(res =>
            res.json()
          )
          .then(data => {
            setLoading(false);
            setData(data);
            alert("합/불 여부 체크 완료")
          })
      }
      )
  }
  const handlePageChange = (page) => {
    setLoading(true)
    setJobpage({ ...jobpage, currentPage: page });
    setLoading(false)
  };

  const { jobdata, pageSize, currentPage } = jobpage;
  const pagedJobs = paginate(data, currentPage, pageSize);
  console.log(pagedJobs);

  const useConfirm = (message = null, onConfirm, onCancel, deleteHR) => {
    if (!onConfirm || typeof onConfirm !== "function") {
      return;
    }
    if (onCancel && typeof onCancel !== "function") {
      return;
    }

    const confirmAction = () => {
      if (window.confirm(message)) {
        onConfirm();
        deleteHR();
      } else {
        onCancel();
      }
    };
    return confirmAction;
  };

  const deleteConfirm = () => 1;
  const cancelConfirm = () => 0;

  const confirmPassOrNot = useConfirm(
    "합/불 여부 결정 후에는 수정이 불가능합니다. 합/불 여부를 결정하시겠습니까?",
    deleteConfirm,
    cancelConfirm,
    PassOrNot
  );
  const jobsNo3 = ChangeJobString(jobsNo);
  console.log("jobsno", jobsNo)

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
                    <div className="card-body">
                      <p className="card-title mb-4">{jobsNo3} 2차 면접 대상자 목록</p>
                      <div className="table-responsive">
                        <table className="table table-striped table-borderless">
                          <thead>
                            <tr>
                              <th>번호</th>
                              <th>수험번호</th>
                              <th>점수 입력</th>
                              <th>채점하기</th>
                              <th>점수</th>
                              <th>합/불 여부</th>
                              <th>채점자</th>
                              <th>최종 결정 담당자</th>
                            </tr>
                          </thead>
                          <tbody>
                            {
                              pagedJobs.length > 0 && pagedJobs.map(
                                (data, idx) => (
                                  <SecondInterviewTableForm
                                    idx={idx + 1}
                                    key={data.idx}
                                    data={data}
                                    jobsNo={jobsNo}
                                    setData={setData}
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
                <div>
                  <button type="button" className="btn btn-primary"
                    style={{ marginTop: "-22px", marginRight: "10px", float: "right" }} onClick={confirmPassOrNot}>합/불 여부 결정하기</button>
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