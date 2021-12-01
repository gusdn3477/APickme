import { useState, useEffect } from "react";
import { ChangeEmpNoString } from "../../../../utilities/ChangeString";


export default function SecondInterviewTableForm({ idx, key, data, jobsNo, setData, secondInterviewPass, setSecondInterviewPass }) {

  const [values, setValues] = useState(data);
  const [loading, setLoading] = useState(false);
  const name2 = ChangeEmpNoString(data.secondInterviewer)
  const name3 = ChangeEmpNoString(data.firstCheck)
  console.log("name", name3);

  const score = () => {

    if(data.secondCheck){
      alert("이미 합/불 여부가 결정된 전형은 재채점이 불가능합니다.");
      return;
    }
    if(values.score === undefined){
      alert("빈 칸은 입력할 수 없습니다.");
      return;
    }
    if(values.score < 0 || values.score > 100){
      alert("0과 100 사이의 숫자만 입력해 주세요.");
      return;
    }
    fetch(`/process-service/process/second-interview/score`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        applyNum: data.applyNum,
        secondInterviewScore: values.score,
        secondInterviewer: localStorage.getItem('empNo')
      }),
    })
      .then(res => {
        // setValues('');
        console.log('데이터 확인용', secondInterviewPass)
        return res;
      })
      .then(
        res =>
          fetch(`/process-service/process/second-interview/${jobsNo}`)
            .then(res => {
              return res.json();
            })
        .then(data => {
          // setData(data);
          const data2 = data.filter(data => data.firstInterviewResult === 'P');
          console.log('data2', data2);
          setData(data2);
          alert("채점 완료!");
        })
        )
  }


  const handleChangeForm = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value
    });
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

  return (
    <tr>
      <td>{idx}</td>
      <td>{data.applyNum ? (data.applyNum).substring(0,8) : ""}</td>
      <td><input type="number" class="form-control" id="exampleInputPassword1" name="score" placeholder="Only Number" onChange={handleChangeForm} /></td>
      <td><button type="button" className="btn btn-primary" onClick={confirmScore}>채점하기</button></td>
      <td>{data.secondInterviewScore}</td>
      <td>{data.secondInterviewResult ? data.secondInterviewResult : "미정"}</td>
      <td>{data.secondInterviewer ? name2 : ""}</td>
      {/* <td>{data.secondInterviewer ? (data.secondInterviewer).substring(0,8) : ""}</td> */}
      <td>{data.secondCheck ? name3 : ""}</td>
    </tr>
  );
}