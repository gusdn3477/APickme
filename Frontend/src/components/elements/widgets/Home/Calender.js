import React from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useState, useEffect } from "react";

export default function Calender() {
  console.log("corpNo :" + `${localStorage.getItem("corpNo")}`);
  console.log("userId :" + `${localStorage.getItem("userId")}`);
  const [events, setEvents] = useState([]);

  //localhost:8000/job-service/jobsall/4b18192d-22d6-4183-959e-225112966c8c
  //localhost:8000/job-service/jobsall/user
  useEffect(() => {
    if (localStorage.getItem("corpNo")) {
      fetch(`job-service/jobsall/${localStorage.getItem("corpNo")}`)
        .then((res) => {
          return res.json();
        })
        .then((events) => {
          console.log(events);
          setEvents(events);
        });
    } else if (localStorage.getItem("userId")) {
      fetch(`job-service/jobsall/user`)
        .then((res) => {
          return res.json();
        })
        .then((events) => {
          console.log(events);
          setEvents(events);
        });
    }
  }, []);

  return (
    <div className="App">
      <FullCalendar
        plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        headerToolbar={{
          //center: "dayGridMonth,timeGridWeek,timeGridDay new",
          
        }}
        // customButtons={{
        //   dayGridMonth: {
        //     text: "Month",
            
        //     backgroundColor: "#3c75b7",
        //   },
        // }}
        events={events}
        week
        
        eventColor="sky"
        eventTextColor="white"
        eventColor="#596fc5"
        nowIndicator
        dateClick={(e) => console.log(e.dateStr)}
        eventClick={(e) => console.log(e.event.id)}
      />
    </div>
  );
}
