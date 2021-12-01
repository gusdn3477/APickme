import React from "react";
import { useState, useEffect } from "react";
import Footer from "../../../layout/Footer";
import Banner from "../../ui/Banner";
import Header from "../../../layout/Header";
import {
  LineChart,
  Line,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
} from "recharts";

///apply/{corpNo}/count
export default function ApplyChart() {
  const [events, setEvents] = useState([]);

  console.log(`${localStorage.getItem("corpNo")}`);

  useEffect(() => {
    fetch(`user-service/users/apply/count/${localStorage.getItem("corpNo")}`)
      .then((res) => {
        return res.json();
      })
      .then((events) => {
        console.log(events);
        setEvents(events);
      });
  }, []);

  return (
    <div id="wrap">
      <Header />
      <div className="container-scroller">
        <div className="container-fluid page-body-wrapper">
          <Banner />
          <div className="main-panel">
            <div className="content-wrapper">
              <div className="row">
                <h3 id="staticTitle" style={{ textAlign: "center" }}>
                  공고별 지원 현황
                </h3>
                <LineChart
                  width={1500}
                  height={600}
                  data={events}
                  margin={{ top: 5, right: 20, bottom: 5, left: 0 }}
                >
                  <Line
                    type="monotone"
                    dataKey="pv"
                    stroke="#8884d8"
                    activeDot={{ r: 8 }}
                  />
                  <Line type="monotone" dataKey="uv" stroke="#82ca9d" />
                  <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                </LineChart>
              </div>
            </div>
            <Footer />
          </div>
        </div>
      </div>
    </div>
  );
}
