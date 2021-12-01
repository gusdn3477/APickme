import { useEffect, useState } from "react";

export default function ChangeJobString(jobsNo) {
    // const jobsNo2 = jobsNo;
    const [jobsNo2,setJobsNo2] = useState("");
    useEffect(()=> {
        fetch(`/job-service/jobs/${jobsNo}`)
        .then(res=>{
            return res.json();
        })
        .then(data => {
            setJobsNo2(data.jobsTitle)
        });
    });


    return jobsNo2;

}
export function ChangeEmpNoString(empNo){
    const [name2, setName] = useState("");
    useEffect(()=>{
        fetch(`/hr-service/hr/detail/${empNo}`)
        .then(res=>{
            return res.json();
        })
        .then(data =>{
            setName(data.name)
        });
    });

    return name2
}

export function GetCorpName(corpNo){
    const [name, setName] = useState("");
    useEffect(()=>{
        fetch(`/hr-service/hr/findcorpname/${corpNo}`)
        .then(res=>{
            return res.json();
        })
        .then(data =>{
            setName(data.corpName)
        });
    });

    return name
}
