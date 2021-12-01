import React, { useState } from 'react';
import data from '../db/data.json';

export default function Listwords() {
    console.log(data);
    //const [myData, setMyData] = useState({data});
    const day = 1;
    const wordLists = data.words.filter( word => word.day === day );
    console.log(wordLists);

    return (
        <div>
            <table>
                {
                    wordLists.map(word=>(
                        <td id={word.id}>
                            <th>{word.eng}</th>
                            <th>{word.kor}</th>
                        </td>
                    ))
                }
            </table>
        </div>
    );
}