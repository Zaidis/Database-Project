import React from 'react';
import Page from 'src/components/Page';
import { csv } from 'csvtojson';
import axios from 'axios';
import './style.css';

class HomeView extends React.Component {

  state = {
    csvData: null,
  };

  async getFile(){
    try{
      // generate a request
      let res1 = await axios.get('vgsales.csv');
      // convert the csv to json with the package
      csv({
          noheader:true,
          output: "csv"
      })
      .fromString(res1.data)
      .then((csvRow)=>{ 
          console.log(csvRow); 
          // add the JSON to the state
          this.setState({ csvData: csvRow });
      });
    }catch(err){
      console.error(err);
    }
  }

  componentDidMount(){
    this.getFile();
  }
  
  render(){
    return (
      <Page title="CSV">
        {/* Start the loop of the edits */}
        <table style={{width: '100%', minWidth: '1000px'}}>
            <tbody>
            {
                this.state.csvData?
                    this.state.csvData.map((row, i) => {
                        return (
                            <tr key={i}>
                                {
                                    row.map((col, x) => {
                                        return (
                                            <td key={x}>
                                                <p>{col}</p>
                                            </td>
                                        );
                                    })
                                }
                            </tr>
                        );
                    })
                :null
            }
            </tbody>
        </table>
      </Page>
    );
  }
}
export default HomeView;