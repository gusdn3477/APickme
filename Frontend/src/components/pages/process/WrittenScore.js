import React, { useState, useEffect } from 'react';
import Banner from '../../elements/ui/Banner';
import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import { useParams } from 'react-router';
import WrittenTableForm from '../../elements/widgets/Form/WrittenTableForm';
import Pagination from '../../../utilities/Pagination';
import { paginate } from '../../../utilities/paginate';
import ChangeJobString from '../../../utilities/ChangeString';

export default function WrittenScore() {

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [writtenPass, setWrittenPass] = useState();
  const [flag, setFlag] = useState(0);
  const { jobsNo } = useParams();
  const [jobpage, setJobpage] = useState({
    jobdata: [],
    pageSize: 8,
    currentPage: 1,
  });

  useEffect(() => {
    fetch(`/process-service/process/written/${jobsNo}`)
      .then(res => {
        return res.json();
      })
      .then(data => {
        setData(data);
        console.log('data', data[0]);
      })
      .then(
        fetch(`/job-service/jobprocess/${jobsNo}`) // 필기 합격 인원 가져오기
          .then(res => {
            return res.json();
          })
          .then(data => {
            setWrittenPass(data);
            setLoading(false);
          })
      );
  }, []);

  const score = () => {

    if(data[0]===undefined) {
      alert("인원이 없습니다");
      return;
    }
    if(data[0].writtenCheck){
      alert("이미 합/불 여부가 결정된 전형은 재채점이 불가능합니다.");
      return;
    }
    setLoading(true);
    fetch(`/process-service/process/written-test/score`, { // 점수 매기는 과정.. => 여기서 점수 바뀜
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        jobsNo: jobsNo
      }),
    })
      .then(res => {
        return res.json()
      })
      .then(res => {
        console.log('res', res);
        return res;
      }
      )
      .then(
        res => {
        fetch(`/process-service/process/written/${jobsNo}`)
          .then(res => {
            return res.json();
          })
          .then(data => {
            setData(data);
            console.log('data', data);
            setFlag(1);
            alert("채점 완료!")
            setLoading(false);
          })}
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


  const PassOrNot = () => { // jobprocess 가져올 수 있어야 함

    if(data[0].writtenCheck){
      alert("이미 합/불 여부가 결정된 전형입니다.");
      return;
    }

    if (flag === 0) {
      alert("채점 후에 합/불 여부를 가릴 수 있습니다");
      return;
    }
    else {
      setLoading(true);
      fetch(`/process-service/process/written-test/result`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          jobsNo: jobsNo,
          empNo: localStorage.getItem('empNo'),
          count: writtenPass.writtenPass
        }),
      }).then(res => res.json()) // 여기 의심스럽긴 합니다
        .then(
          res => {
          fetch(`/process-service/process/written/${jobsNo}`)
            .then(res => {
              return res.json();
            })
            .then(data => {
              setFlag(2);
              setData(data);
              setLoading(false);
              alert("합/불 여부 체크 완료")
            })}
        )
    }
  }

  const PassList = () => { // jobprocess 가져올 수 있어야 함
    if (flag !== 2) {
      alert("합/불 여부가 정해진 뒤에 합격자 명단을 넘길 수 있습니다.");
    }
    else {
      fetch(`/process-service/process/written-test/${jobsNo}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        }
      }).then(alert("명단을 넘겼습니다!"));
    }
  }

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

  const confirmScore = useConfirm(
    "채점하시겠습니까?",
    deleteConfirm,
    cancelConfirm,
    score
  );

  const confirmPassOrNot = useConfirm(
    "합/불 여부를 결정한 후에는 재채점이 불가능합니다. 합/불 여부를 결정하시겠습니까?",
    deleteConfirm,
    cancelConfirm,
    PassOrNot
  );

  const confirmPassList = useConfirm(
    "필기 합격자 명단을 넘기시겠습니까?",
    deleteConfirm,
    cancelConfirm,
    PassList
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
                      <p className="card-title mb-4 ml-3">{jobsNo3} 필기 대상자 목록</p>
                      <div className="table-responsive">
                        <table className="table table-striped table-borderless" style={{marginBottom:"2rem"}}>
                          <thead>
                            <tr>
                              <th>번호</th>
                              <th>수험번호</th>
                              <th>점수</th>
                              <th>합/불 여부</th>
                              <th>최종 결정 담당자</th>
                            </tr>
                          </thead>
                          <tbody>
                            {
                              pagedJobs.length > 0 && pagedJobs.map(
                                (data, idx) => (
                                  <WrittenTableForm
                                    idx={idx + 1}
                                    key={data.idx}
                                    data={data}
                                    jobsNo={jobsNo}
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
                  <form class="row gy-2 gx-3 align-items-center" style={{marginTop:"-22px", float:"right"}}>
                    <div class="col-auto">
                      <button type="button" className="btn btn-primary" onClick={confirmScore}>채점하기</button>
                    </div>
                    <div class="col-auto">
                      <button type="button" className="btn btn-primary" onClick={confirmPassOrNot}>합/불 여부 결정하기</button>
                    </div>
                    <div class="col-auto" style={{marginRight:"10px"}}>
                      <button type="button" className="btn btn-primary" onClick={confirmPassList}>합격자 명단 넘기기</button>
                    </div>
                  </form>
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