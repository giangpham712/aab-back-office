<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <style>

        @page {
            size: A4;
            margin: 0;
        }

        body {
            font-family: Arial;
            font-size: 12px;
        }

        table {

            margin: 0 auto;
            border-collapse: collapse;

        }

        table th {
            font-weight: normal;
        }

        table td,
        table th {
            border: 1px solid #000;
            padding: 0px 5px;
        }

        .no-padding {
            padding: 0;
        }

        table table td {
            border-width: 1px 0;
        }

        table table tr:first-child td {
            border-top-width: 0;
        }

        table table tr:last-child td {
            border-bottom-width: 0;
        }

        table table tr > td:first-child {
            width: 1px;
            padding: 0;
        }

        @media screen {

            body {
                font-size: 14px;
            }

            table th,
            table td {
                height: 24px;
                line-height: 24px;
            }

            table table th,
            table table td {

            }
        }

        @media print {

            body {
                font-size: 12px;
            }

            html, body {
                width: 210mm;
                height: 297mm;
            }

            table#main {
                width: 95%;
            }

            table table {
                width: 100%;
                height: 100%;
            }

            table tr th,
            table tr td {
                height: 19px;
                line-height: 19px;
            }

        }


    </style>
</head>
<body>
<div style="height: 40px">

</div>
<table id="main">

    <thead>

    <tr>
        <th rowspan="2" style="border-width: 2px">Thông tin sản phẩm và yêu cầu sản xuất</th>
        <th colspan="2">Thời gian</th>
        <th rowspan="2" style="text-align: center; width: 30px">Số cuộn</th>
        <th colspan="2">Khối lượng</th>
        <th rowspan="2">Máy số</th>
        <th colspan="2">Thời gian</th>
        <th rowspan="2">Số cuộn</th>
        <th colspan="2">Khối lượng</th>
        <th rowspan="2">Máy số</th>
    </tr>
    <tr>
        <th>Ca</th>
        <th>Ngày</th>
        <th>M</th>
        <th>KG</th>
        <th>Ca</th>
        <th>Ngày</th>
        <th>M</th>
        <th>KG</th>
    </tr>

    </thead>


    <tbody>
    <tr>
        <td class="no-padding" rowspan="50" style="width: 20%; border-width: 2px">
            <table class="nested" style="width: 100%">
                <tbody>
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">Khách hàng: KATOH</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">PO 163: Nishimatsuya</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">Sản phẩm:</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">NISHIMATSUYA S</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">Quy cách:</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">0.020x270/400x375</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>Chiều rộng:</td>
                    <td><b>270 MM</b></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>Chiều dài:</td>
                    <td><b>375 MM</b></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>Gấp hông:</td>
                    <td><b>65.00 MM</b></td>
                </tr>
                <tr>
                    <td style="border-bottom-color: transparent">&nbsp;</td>
                    <td rowspan="2">Định lượng:</td>
                    <td rowspan="2"><b>13.68 G/M</b></td>
                </tr>
                <tr>
                    <td style="border-top-color: transparent">&nbsp;</td>
                </tr>
                <tr>
                    <td style="border-bottom-color: transparent">&nbsp;</td>
                    <td rowspan="2">4000 M = </td>
                    <td rowspan="2">54.72 KG</td>
                </tr>
                <tr>
                    <td style="border-top-color: transparent">&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">Khối lượng cần sản xuất</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td></td>
                    <td</td>
                </tr>
                <tr>
                    <td style="border-bottom-color: transparent">&nbsp;</td>
                    <td colspan="2" rowspan="2" style="text-align: center">151 cuộn</td>
                </tr>
                <tr>
                    <td style="border-top-color: transparent">&nbsp;</td>
                </tr>
                <tr>
                    <td style="border-bottom-color: transparent">&nbsp;</td>
                    <td colspan="2" rowspan="2" style="text-align: center">7878 KG</td>
                </tr>
                <tr>
                    <td style="border-top-color: transparent">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr>
                <#list 22..50 as i>
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">&nbsp;</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </td>
        <td></td>
        <td></td>
        <td style="text-align: center; width: 5%">1</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td style="text-align: center; width: 5%">51</td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <#list 2..50 as i>
    <tr>
        <td></td>
        <td></td>
        <td style="text-align: center; width: 5%">${i}</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td style="text-align: center; width: 5%">${i + 50}</td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    </#list>

    </tbody>

</table>
</body>
</html>