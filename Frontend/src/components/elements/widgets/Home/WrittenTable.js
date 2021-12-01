import React, { useState, useEffect } from 'react';
import WrittenTableForm from '../Form/WrittenTableForm';

export default function WrittenTable() {

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

  const handleDelete = (id) => {
    fetch(`/catalog-service/catalogs/${id}`, {
      method: "DELETE"
    }).then(
      alert("삭제 되었습니다!"),
      fetch(`/catalog-service/catalogs`)
        .then(res => {
          return res.json();
        })
        .then(data => {
          // setCartDatas(data);
        })
    )
  }

// 채점하는 함수
  const handleScore = () => {
    fetch(`/process-service/processs/written-test/score`, {
      method: "PUT",
      headers: {
        "Content-Type" : "application/json"
      },
      body: JSON.stringify({
        empNo : data.empNo,
        jobsNo : data.jobsNo
      })
    }).then(
      alert("채점을 완료하였습니다!"),
      fetch(`/catalog-service/catalogs`) // 필기 면접자..
        .then(res => {
          return res.json();
        })
        .then(data => {
          setData(data);
        })
    )
  }

  const handleToInterview = () => {
    fetch(`/process-service/process/written-test/result`, {
      method: "PUT"
    }).then(
      alert("명단을 넘겼습니다!"),
      fetch(`/catalog-service/catalogs`)
        .then(res => {
          return res.json();
        })
        .then(data => {
          setData(data);
        })
    )
  }

  return (
    <div className="col-md-12 grid-margin stretch-card">
      <div className="card">
        <div className="card-body">
          <p className="card-title">필기전형 대상자 목록</p>
          <div className="row">
            <div className="col-12">
              <div className="table-responsive">
                <table id="example" className="table table-striped table-borderless" style={{ width: "100%" }}>
                  <thead>
                    <tr>
                      <th>수험번호</th>
                      <th>이름</th>
                      <th>필기점수</th>
                      <th>필기 합/불 여부</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                      data.length > 0 && data.map(
                        (item => {
                          <WrittenTableForm
                            key={item.id}
                            data={item}
                            setMyList={setData}
                          />
                        })
                      )
                    }
                    <tr>
                      <th>15010701</th>
                      <th>박현우</th>
                      <th>98</th>
                      <th>미정</th>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div className="mt-3">
          <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn col-md-4" onClick={()=>handleScore()}>채점하기</button>
          {/* <a className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn col-md-4" onClick={()=>handleScore()}>채점하기</a> */}
        </div>
        <div className="mt-3">
          <button type="button" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn col-md-4" onClick={()=>handleToInterview()}>합격자 명단 넘겨주기(채점 완료시에 가능)</button>
          {/* <a className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn col-md-4" href="../../index.html">합격자 명단 넘겨주기(채점 완료시에 가능)</a> */}
        </div>
      </div>
    </div>
  );
}