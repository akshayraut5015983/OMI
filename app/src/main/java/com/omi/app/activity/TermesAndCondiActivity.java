package com.omi.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omi.app.R;

public class TermesAndCondiActivity extends AppCompatActivity {
    LinearLayout rest, landry;
    TextView tvres, tvlan;
    String strRest = "", strLan = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termes_condition);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tvres = findViewById(R.id.tvrest);
        tvlan = findViewById(R.id.tvlan);
        rest = findViewById(R.id.rest);
        landry = findViewById(R.id.lan);
        onTest();
        tvres.setText(Html.fromHtml(strRest));
        tvlan.setText(Html.fromHtml(strLan));

    }

    private void onTest() {
        strRest = "This document is an electronic record in terms of Information Technology Act, 2000 that require publishing the\n" +
                "rules and regulations, privacy policy and Terms of Use for access or usage of www.omionline.in website and\n" +
                "OMI application for mobile.\n" + "<br>" + "<br>" + "<b>" +
                "Terms Of Use\n" + "</b>" + "<br>" + "<br>" +
                "I. These terms of use (the “ Terms of Use”) govern your use of our website www.omionline.in (the\n" +
                "“ Website”) and our “OMI” application for mobile (the “ App”). The Website and the App are jointly\n" +
                "referred to as the “ Platform”. Please read these Terms of Use carefully before you use the services. If\n" +
                "you do not agree to these Terms of Use, you may not use the services on the Platform, and we request\n" +
                "you to uninstall the App. By installing, downloading or even merely using the Platform, you shall be\n" +
                "contracting with OMI and you signify your acceptance to the Terms of Use .\n" + "<br>" + "<br>" +
                "II. The Platform is operated and owned by AGN Digital Services Private Limited, a company incorporated\n" +
                "under the Companies Act, 2013 and having its registered office at Mahavir Colony,\n" +
                "Salchak,Anishabad,Phulwari, Patna , Bihar-800002. For the purpose of these Terms of Use, wherever\n" +
                "the context so requires, ”you” shall mean any natural or legal person who has agreed to become a buyer\n" +
                "or customer on the Platform by providing Registration Data while registering on the Platform as a\n" +
                "registered user using any computer systems. The terms “OMI”, “we”, “us” or “our” shall mean AGN\n" +
                "Digital Services Private Limited.\n" + "<br>" + "<br>" +
                "III. OMI enables transactions between participant restaurants/merchants and buyers, dealing in prepared\n" +
                "food and beverages (“ Platform Services”). The buyers (“Buyer/s”) can choose and place orders\n" +
                "(“ Orders”) from variety of products listed and offered for sale by various neighbourhood merchants\n" +
                "including but not limited to the restaurants and eateries (“ Merchant/s”), on the Platform and OMI\n" +
                "enables delivery of such orders at select localities of serviceable cities across India (“ Delivery\n" +
                "Services”). The Platform Services and Delivery Services are collectively referred to as “ Services”.\n" + "<br>" + "<br>" +
                "IV. These Terms of Use are subject to modifications at any time. We reserve the right to modify or change\n" +
                "these Terms of Use and other OMI policies at any time by posting changes on the Platform, and you\n" +
                "shall be liable to update yourself of such changes, if any, by accessing the changes on the Platform.\n" +
                "You shall, at all times, be responsible for regularly reviewing the Terms of Use and the other OMI\n" +
                "policies and note the changes made on the Platform.\n" + "<br>" + "<br>" + "<b>" +
                "Use of Platform and Services\n" + "</b>" + "<br>" + "<br>" +
                "1. All commercial/contractual terms are offered by and agreed to between Buyers and Merchants alone. The\n" +
                "commercial/contractual terms include without limitation price, taxes, shipping costs, payment methods,\n" +
                "payment terms, date, period and mode of delivery, warranties related to products and services and after\n" +
                "sales services related to products and services. OMI does not have any control or does not determine or\n" +
                "advise or in any way involve itself in the offering or acceptance of such commercial/contractual terms\n" +
                "between the Buyers and Merchants. OMI may, however, offer support services to Merchants in respect to\n" +
                "order fulfilment, payment collection, call centre, and other services, pursuant to independent contracts\n" +
                "executed by it with the Merchants.\n" + "<br>" + "<br>" +
                "2. OMI does not make any representation or warranty as to the item-specifics (such as legal title,\n" +
                "creditworthiness, identity, etc.) of any of the Merchants. You are advised to independently verify the bona\n" +
                "fides of any particular Merchant that you choose to deal with on the Platform and use your best judgment\n" +
                "in that behalf. All Merchant offers and third party offers are subject to respective party terms and\n" +
                "conditions. OMI takes no responsibility for such offers.\n" + "<br>" + "<br>" +
                "3. OMI neither make any representation or warranty as to specifics (such as quality, value, salability, etc.) of\n" +
                "the products or services proposed to be sold or offered to be sold or purchased on the Platform nor does\n" +
                "implicitly or explicitly support or endorse the sale or purchase of any products or services on the Platform.\n" +
                "OMI accepts no liability for any errors or omissions, whether on behalf of itself or third parties.\n" + "<br>" + "<br>" +
                "4. OMI is not responsible for any non-performance or breach of any contract entered into between Buyers\n" +
                "and Merchants on the Platform. OMI cannot and does not guarantee that the concerned Buyers and/or\n" +
                "Merchants will perform any transaction concluded on the Platform. OMI is not responsible for\n" +
                "unsatisfactory or non-performance of services or damages or delays as a result of products which are out\n" +
                "of stock, unavailable or back ordered.\n" + "<br>" + "<br>" +
                "5. OMI is operating an online marketplace and assumes the role of facilitator, and does not at any point of\n" +
                "time during any transaction between Buyer and Merchant on the Platform come into or take possession of\n" +
                "any of the products or services offered by Merchant. At no time shall OMI hold any right, title or interest\n" +
                "over the products nor shall OMI have any obligations or liabilities in respect of such contract entered into\n" +
                "between Buyer and Merchant.\n" +
                "\n" + "<br>" + "<br>" +
                "6. OMI is only providing a platform for communication and it is agreed that the contract for sale of any of the\n" +
                "products or services shall be a strictly bipartite contract between the Merchant and the Buyer. In case of\n" +
                "complaints from the Buyer pertaining to food efficacy, quality, or any other such issues, OMI shall notify the\n" +
                "same to Merchant and shall also redirect the Buyer to the consumer call center of the Merchant. The\n" +
                "Merchant shall be liable for redressing Buyer complaints. In the event you raise any complaint on any\n" +
                "Merchant accessed using our Platform, we shall assist you to the best of our abilities by providing relevant\n" +
                "information to you,such as details of the Merchant and the specific Order to which the complaint relates, to\n" +
                "enable satisfactory resolution of the complaint.\n" + "<br>" + "<br>" +
                "7. You may access the Platform by registering to create an account (“ OMI Account”) and become a member\n" +
                "(“ Membership”); or (c) you can also register to join by logging into your account with certain third party\n" +
                "social networking sites (“ SNS”) (including, but not limited to, Facebook); each such account, a “ Third\n" +
                "Party Account”, via our Platform, as described below. The Membership is limited for the purpose and are\n" +
                "subject to the terms, and strictly not transferable. As part of the functionality of the Platform services, you\n" +
                "may link your OMI Account with Third Party Accounts, by either:\n" +
                "\n" + "<br>" +
                "i. providing your Third Party Account login information to us through the Platform; or\n" + "<br>" +
                "ii. allowing us to access your Third Party Account, as is permitted under the applicable\n" +
                "terms and conditions that govern your use of each Third Party Account.\n" + "<br>" + "<br>" +
                "8. You represent that you are entitled to disclose your Third Party Account login information to us and/or\n" +
                "grant us access to your Third Party Account (including, but not limited to, for use for the purposes\n" +
                "described herein), without breach by you of any of the terms and conditions that govern your use of the\n" +
                "applicable Third Party Account and without obligating us to pay any fees or making us subject to any\n" +
                "usage limitations imposed by such third party service providers.\n" + "<br>" + "<br>" +
                "9. By granting us access to any Third Party Accounts, you understand that we will access, make available\n" +
                "and store (if applicable) any content or information that you have provided to and stored in your Third Party\n" +
                "Account (“ SNS Content”) so that it is available on and through the Platform via your OMI Account.\n" + "<br>" + "<br>" +
                "10. Unless otherwise specified in these Terms of Use, all SNS Content, if any, will be considered to be your\n" +
                "content for all purposes of these Terms of Use.\n" + "<br>" + "<br>" +
                "11. Depending on the Third Party Accounts, you choose, and subject to the privacy settings that you have set\n" +
                "in such Third Party Accounts, personally identifiable information that you post to your Third Party Accounts\n" +
                "will be available on and through your OMI Account on the Platform.\n" + "<br>" + "<br>" +
                "12. Please note that if a Third Party Account or associated service becomes unavailable or our access to such\n" +
                "Third Party Account is terminated by the third party service provider, then SNS Content will no longer be\n" +
                "available on and through the Platform.\n" + "<br>" + "<br>" +
                "13. We will create your OMI Account for your use of the Platform services based upon the personal information\n" +
                "you provide to us or that we obtain via an SNS, as described above. You may only have one OMI Account\n" +
                "and not permitted to create multiple accounts. If found, you having multiple accounts, OMI reserves right to\n" +
                "suspend such multiple account without being liable for any compensation.\n" + "<br>" + "<br>" +
                "14. You agree to provide accurate, current and complete information during the registration process and to\n" +
                "update such information to keep it accurate, current and complete.\n" + "<br>" + "<br>" +
                "15. We reserve the right to suspend or terminate your OMI Account and your access to the Services "+ "<br>" +"(i) if any\n" +
                "information provided during the registration process or thereafter proves to be inaccurate, not current or\n" +
                "incomplete; "+ "<br>" +"(ii) if it is believed that your actions may cause legal liability for you, other users or us; and/or\n" +"<br>"+
                "(iii) if you are found to be non- compliant with the Terms of Use.\n" + "<br>" + "<br>" +
                "16. You are responsible for safeguarding your password. You agree that you will not disclose your password to\n" +
                "any third party and that you will take sole responsibility for any activities or actions under your OMI\n" +
                "Account, whether or not you have authorized such activities or actions. You will immediately notify us of\n" +
                "any unauthorized use of your OMI Account.\n" + "<br>" + "<br>" +
                "17. Goods and services purchased from the Platform are intended for your personal use and you represent\n" +
                "that the same are not for resale or you are not acting as an agent for other parties.\n" +"<br>" +"<br>" +
                "18. The Platform allows you to place food order bookings and we will, subject to the terms and conditions set\n" +
                "out herein, enable delivery of such order to you.\n" + "<br>" + "<br>" +
                "19. OMI do not own, sell, resell on its own and/or do not control the Merchants or the related services provided\n" +
                "in connection thereof. You understand that any order that you place shall be subject to the terms and\n" +
                "conditions set out in these Terms of Use including, but not limited to, product availability and delivery\n" +
                "location serviceability.\n" +
                "\n" + "<br>" + "<br>" +
                "20. As a general rule, all food orders placed on the Platform are treated as confirmed.\n" + "<br>" +"<br>" +
                "21. However, upon your successful completion of booking an order, we may call you on the telephone or\n" +
                "mobile number provided to confirm the details of the order, the price to be paid and the estimated delivery\n" +
                "time. For this purpose, you will be required to share certain information with us, including but not limited to\n" +"<br>"+
                "(i) your first and last name "+"<br>"+"(ii) mobile number; and "+"<br>"+"(iii) email address. It shall be your sole responsibility to\n" +
                "bring any incorrect details to our attention.\n" + "<br>" + "<br>" +
                "22. In addition to the foregoing, we may also contact you by phone and / or email to inform and confirm any\n" +
                "change in the order, due to availability or unavailability or change in price of the order as informed by the\n" +
                "Merchant. Please note change or confirmation of the order shall be treated as final. It is clarified that OMI\n" +
                "reserves the right to not to process your order in the event you are unavailable on the phone at the time we\n" +
                "call you for confirming the order and such event the provisions of the cancellation and refund policy below\n" +
                "shall be applicable.\n" + "<br>" + "<br>" +
                "23. You acknowledge and agree that we act as the Merchant’s payment agent for the limited purpose of\n" +
                "accepting payments from you on behalf of the Merchant. Upon your payment of amounts to us, which are\n" +
                "due to the Merchant, your payment obligation to the Merchant for such amounts is completed, and we are\n" +
                "responsible for remitting such amounts, to the Merchant. You shall not, under any circumstances\n" +
                "whatsoever, make any payment directly to the Merchant for Order bookings made using the Platform.\n" + "<br>" + "<br>" +
                "24. You agree to pay us for the total amount for the order placed by you on the Platform. We will collect the\n" +
                "total amount in accordance with the terms and conditions of these Terms of Use and the pricing terms set\n" +
                "forth in the applicable menu listing for the particular Merchant. Please note that we cannot control any\n" +
                "amount that may be charged to you by your bank related to our collection of the total amount, and we\n" +
                "disclaim all liability in this regard.\n" + "<br>" + "<br>" +
                "25. The final tax bill will be issued by the Merchant to the Buyer along with the order and OMI is merely\n" +
                "collecting the payment on behalf of such Merchant. All applicable taxes and levies, the rates thereof and\n" +
                "the manner of applicability of such taxes on the bill are being charged and determined by the Merchant.\n" +
                "OMI holds no responsibility for the legal correctness/validity of the levy of such taxes. The sole\n" +
                "responsibility for any legal issue arising on the taxes shall reside with the Merchant.\n" + "<br>" + "<br>" +
                "26. The prices reflected on the Platform are determined solely by the Merchant and are listed based on\n" +
                "Merchant’s information. Very rarely, prices may change at the time of placing order due to Merchant\n" +
                "changing the menu price without due intimation and such change of price are at the sole discretion of the\n" +
                "Merchant attributing to various factors beyond control.\n" +
                "Disclaimer: Prices on any product(s) as is reflected on the Platform may due to some technical\n" +
                "issue, typographical error or product information supplied by Merchant be incorrectly reflected\n" +
                "and in such an event Merchant may cancel such your order(s).\n" +
                "\n" + "<br>" + "<br>" +
                "27. The Merchant shall be solely responsible for any warrantee/guarantee of the food products sold to the\n" +
                "Buyers and in no event shall be the responsibility of OMI.\n" + "<br>" + "<br>" +
                "28. The transaction is bilateral between the Merchant and Buyer and therefore, OMI is not liable to charge or\n" +
                "deposit any taxes applicable on such transaction.\n" + "<br>" + "<br>" + "<b>" +
                "V. Cancellations and Refunds\n" + "</b>" + "<br>" + "<br>" +
                "1. Cancellation\n" +
                "\n" + "<br>" + "<br>" +
                "i. As a general rule you shall not be entitled to cancel your order once you have\n" +
                "received confirmation of the same. If you cancel your order after it has been\n" +
                "confirmed, OMI shall have a right to charge you cancellation fee of a minimum INR 50\n" +
                "upto the order value, with a right to either not to refund the order value or recover from\n" +
                "your subsequent order, the complete/ deficit cancellation fee, as applicable, to\n" +
                "compensate our restaurant and delivery partners. OMI shall also have right to charge\n" +
                "you cancellation fee for the orders cancelled by OMI for the reasons of this\n" +
                "cancellation and refunds policy. In case of cancellations for the reasons attributable to\n" +
                "OMI or its restaurant and delivery partners, OMI shall not charge you any cancellation\n" +
                "fee.\n" + "<br>" + "<br>" +
                "ii. However, in the unlikely event of an item on your order being unavailable, we will\n" +
                "contact you on the phone number provided to us at the time of placing the order and\n" +
                "inform you of such unavailability. In such an event you will be entitled to cancel the\n" +
                "entire order and shall be entitled to a refund in accordance with our refund policy.\n" + "<br>" + "<br>" +
                "iii. We reserve the sole right to cancel your order in the following circumstance:\n" +
                "\n" +
                "a. in the event of the designated address falls outside the delivery zone offered\n" +
                "by us;\n" +
                "b. failure to contact you by phone or email at the time of confirming the order\n" +
                "booking;\n" +
                "c. failure to deliver your order due to lack of information, direction or\n" +
                "authorization from you at the time of delivery; or\n" +
                "d. unavailability of all the items ordered by you at the time of booking the order;\n" +
                "or\n" +
                "e. unavailability of all the items ordered by you at the time of booking the order;\n" +
                "or\n" +
                "\n" + "<b>" + "<br>" + "<br>" +
                "2. Refunds\n" + "</b>" +
                "\n" + "<br>" + "<br>" +
                "i. You shall be entitled to a refund only if you pre-pay for your order at the time of\n" +
                "placing your order on the Platform and only in the event of any of the following\n" +
                "circumstances:\n" +
                "a. your order packaging has been tampered or damaged at the time of delivery;\n" +
                "b. us cancelling your order due to "+"<br>"+"(A) your delivery location following outside\n" +
                "our designated delivery zones; "+"<br>"+"(B) failure to contact you by phone or email at\n" +
                "the time of confirming the order booking; or "+"<br>"+"(C) failure to contact you by\n" +
                "phone or email at the time of confirming the order booking; or\n" +
                "c. you cancelling the order at the time of confirmation due to unavailability of\n" +
                "the items you ordered for at the time of booking.\n" +
                "\n" + "<br>" + "<br>" +
                "ii. Our decision on refunds shall be at our sole discretion and shall be final and binding.\n" + "<br>" + "<br>" +"<br>" +
                "iii. All refund amounts shall be credited to your account within 3-4 business days in\n" +
                "accordance with the terms that may be stipulated by the bank which has issued the\n" +  "<br>" +"<br>" + "<br>" +"<b>" +
                "credit / debit card.\n" + "</b>" + "<br>" + "<br>" +
                "\n" +
                "3. In case of payment at the time of delivery, you will not be required to pay for:\n" + "<br>" + "<br>" +"<br>" +
                "i. orders where the packaging has been tampered or damaged by us;\n" + "<br>" +"<br>" +
                "ii. wrong order being delivered; or\n" + "<br>" +"<br>" +
                "iii. items missing from your order at the time of delivery.\n" +
                "\n" + "<br>" + "<br>" +"<br>" + "<b>" +
                "Disclaimers\n" + "</b>" + "<br>" + "<br>" +
                "4. THE PLATFORM MAY BE UNDER CONSTANT UPGRADES, AND SOME FUNCTIONS AND FEATURES\n" +
                "MAY NOT BE FULLY OPERATIONAL.\n" + "<br>" + "<br>" +
                "5. DUE TO THE VAGARIES THAT CAN OCCUR IN THE ELECTRONIC DISTRIBUTION OF INFORMATION\n" +
                "AND DUE TO THE LIMITATIONS INHERENT IN PROVIDING INFORMATION OBTAINED FROM\n" +
                "MULTIPLE SOURCES, THERE MAY BE DELAYS, OMISSIONS, OR INACCURACIES IN THE CONTENT\n" +
                "PROVIDED ON THE PLATFORM OR DELAY OR ERRORS IN FUNCTIONALITY OF THE PLATFORM.\n" +
                "AS A RESULT, WE DO NOT REPRESENT THAT THE INFORMATION POSTED IS CORRECT IN\n" +
                "EVERY CASE.\n" + "<br>" + "<br>" +
                "6. WE EXPRESSLY DISCLAIM ALL LIABILITIES THAT MAY ARISE AS A CONSEQUENCE OF ANY\n" +
                "UNAUTHORIZED USE OF CREDIT/ DEBIT CARDS.\n" + "<br>" + "<br>" +
                "7. YOU ACKNOWLEDGE THAT THIRD PARTY SERVICES ARE AVAILABLE ON THE PLATFORM. WE\n" +
                "MAY HAVE FORMED PARTNERSHIPS OR ALLIANCES WITH SOME OF THESE THIRD PARTIES\n" +
                "FROM TIME TO TIME IN ORDER TO FACILITATE THE PROVISION OF CERTAIN SERVICES TO YOU.\n" +
                "HOWEVER, YOU ACKNOWLEDGE AND AGREE THAT AT NO TIME ARE WE MAKING ANY\n" +
                "REPRESENTATION OR WARRANTY REGARDING ANY THIRD PARTY’S SERVICES NOR WILL WE\n" +
                "BE LIABLE TO YOU OR ANY THIRD PARTY FOR ANY CONSEQUENCES OR CLAIMS ARISING FROM\n" +
                "OR IN CONNECTION WITH SUCH THIRD PARTY INCLUDING, AND NOT LIMITED TO, ANY LIABILITY\n" +
                "\n" +
                "OR RESPONSIBILITY FOR, DEATH, INJURY OR IMPAIRMENT EXPERIENCED BY YOU OR ANY\n" +
                "THIRD PARTY. YOU HEREBY DISCLAIM AND WAIVE ANY RIGHTS AND CLAIMS YOU MAY HAVE\n" +
                "AGAINST US WITH RESPECT TO THIRD PARTY’S / MERCHANTS SERVICES.\n" + "<br>" + "<br>" +
                "8. OMI DISCLAIMS AND ALL LIABILITY THAT MAY ARISE DUE TO ANY VIOLATION OF THE FOOD\n" +
                "SAFETY AND STANDARDS ACT, 2006 AND APPLICABLE RULES AND REGULATIONS MADE\n" +
                "THEREUNDER AND SUCH LIABILITY SHALL BE ATTRIBUTABLE TO THE MERCHANT.\n" + "<br>" + "<br>" +
                "9. WHILE THE MATERIALS PROVIDED ON THE PLATFORM WERE PREPARED TO PROVIDE\n" +
                "ACCURATE INFORMATION REGARDING THE SUBJECT DISCUSSED, THE INFORMATION\n" +
                "CONTAINED IN THESE MATERIALS IS BEING MADE AVAILABLE WITH THE UNDERSTANDING THAT\n" +
                "WE MAKE NO GUARANTEES, REPRESENTATIONS OR WARRANTIES WHATSOEVER, WHETHER\n" +
                "EXPRESSED OR IMPLIED, WITH RESPECT TO PROFESSIONAL QUALIFICATIONS, EXPERTISE,\n" +
                "QUALITY OF WORK OR OTHER INFORMATION HEREIN. FURTHER, WE DO NOT, IN ANY WAY,\n" +
                "ENDORSE ANY SERVICE OFFERED OR DESCRIBED HEREIN. IN NO EVENT SHALL WE BE LIABLE\n" +
                "TO YOU OR ANY THIRD PARTY FOR ANY DECISION MADE OR ACTION TAKEN IN RELIANCE ON\n" +
                "SUCH INFORMATION.\n" + "<br>" + "<br>" +
                "10. THE INFORMATION PROVIDED HEREUNDER IS PROVIDED “AS IS”. WE AND / OR OUR\n" +
                "EMPLOYEES MAKE NO WARRANTY OR REPRESENTATION REGARDING THE TIMELINESS,\n" +
                "CONTENT, SEQUENCE, ACCURACY, EFFECTIVENESS OR COMPLETENESS OF ANY\n" +
                "INFORMATION OR DATA FURNISHED HEREUNDER OR THAT THE INFORMATION OR DATA\n" +
                "PROVIDED HEREUNDER MAY BE RELIED UPON. MULTIPLE RESPONSES MAY USUALLY BE MADE\n" +
                "AVAILABLE FROM DIFFERENT SOURCES AND IT IS LEFT TO THE JUDGEMENT OF USERS BASED\n" +
                "ON THEIR SPECIFIC CIRCUMSTANCES TO USE, ADAPT, MODIFY OR ALTER SUGGESTIONS OR\n" +
                "USE THEM IN CONJUNCTION WITH ANY OTHER SOURCES THEY MAY HAVE, THEREBY\n" +
                "ABSOLVING US AS WELL AS OUR CONSULTANTS, BUSINESS ASSOCIATES, AFFILIATES,\n" +
                "BUSINESS PARTNERS AND EMPLOYEES FROM ANY KIND OF PROFESSIONAL LIABILITY.\n" + "<br>" + "<br>" +
                "11. WE SHALL NOT BE LIABLE TO YOU OR ANYONE ELSE FOR ANY LOSSES OR INJURY ARISING\n" +
                "OUT OF OR RELATING TO THE INFORMATION PROVIDED ON THE PLATFORM. IN NO EVENT WILL\n" +
                "WE OR OUR EMPLOYEES, AFFILIATES, AUTHORS OR AGENTS BE LIABLE TO YOU OR ANY THIRD\n" +
                "PARTY FOR ANY DECISION MADE OR ACTION TAKEN BY YOUR RELIANCE ON THE CONTENT\n" +
                "CONTAINED HEREIN.\n" + "<br>" + "<br>" +
                "12. IN NO EVENT WILL WE BE LIABLE FOR ANY DAMAGES (INCLUDING, WITHOUT LIMITATION,\n" +
                "DIRECT, INDIRECT, INCIDENTAL, SPECIAL, CONSEQUENTIAL OR EXEMPLARY DAMAGES,\n" +
                "DAMAGES ARISING FROM PERSONAL INJURY/WRONGFUL DEATH, AND DAMAGES RESULTING\n" +
                "FROM LOST PROFITS, LOST DATA OR BUSINESS INTERRUPTION), RESULTING FROM ANY\n" +
                "SERVICES PROVIDED BY ANY THIRD PARTY OR MERCHANT ACCESSED THROUGH THE\n" +
                "PLATFORM, WHETHER BASED ON WARRANTY, CONTRACT, TORT, OR ANY OTHER LEGAL\n" +
                "THEORY AND WHETHER OR NOT WE ARE ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.";

        strLan = "<b>" + "AGN Digital Services Pvt Ltd, primarily operates, controls and manages the Services (as\n" +
                "defined below) provided by it from its corporate office at 3 rd Floor, Urmila Kunj,\n" +
                "Aakashwani Road,Khajpura, Bailey Road Patna-14.\n" + "</b>" + "<br>" + "<br>" +
                "Laundry services are provided by Mobile App “OMI App” or by Website\n" +
                "www.omionline.in to our (&quot;Customer&quot;) and AGN Digital Services Pvt Limited (hereinafter\n" +
                "&quot;Company&quot;) .compliance and acceptance with the terms and conditions set forth below.\n" +
                "Please read the following agreement carefully. &quot;Customer&quot; use of Mobile App “OMI App”\n" +
                "or by Website www.omionline.in indicates an agreement to be bound by the terms and\n" +
                "conditions set forth below.\n" +
                "Items which are given to &quot;Company&quot; for cleaning / repairing will be termed as article(s) in\n" +
                "this agreement.\n" +
                "This agreement is strictly between Company and the Customer and does not in any way\n" +
                "constitute or imply any relationship with any other parties. As a condition to using any of\n" +
                "Company&#39;s services, and for the mutual benefit of both Company and the Customer, the\n" +
                "undersigned (&quot;Customer&quot;) agrees to the following terms and conditions:\n" + "<br>" + "<br>" +
                "\uF0B7 1. All garments/linen/fabrics are handled with greatest care but owing to the conditions\n" +
                "of the articles or non apparent/non-visible defects in its material there is a POSSIBILITY\n" +
                "OF DISCOLOURING OR SHRINKAGE. Such garments are accepted for cleaning at\n" +
                "OWNER&#39;S RISK and company will not accept any responsibility for it.\n" + "<br>" + "<br>" +
                "\uF0B7 2. Company will use reasonable efforts to try to ensure that washing, drying and folding\n" +
                "services are maintained at a high level of quality.\n" + "<br>" + "<br>" +
                "\uF0B7 3. Company due to time constraints does not read manufacturer suggested care and\n" +
                "washing / drying labels, and will not responsible for garments labeled &quot;hand wash only&quot;\n" +
                "or &quot;dry clean only&quot;\n" + "<br>" + "<br>" +
                "\uF0B7 4. Company accepts no liability for &quot;special care&quot; and delicate items that require special\n" +
                "attention to be cleaned.\n" + "<br>" + "<br>" +
                "\uF0B7 5. Company reserves the right to refuse cleaning any garment.\n" + "<br>" + "<br>" +
                "\uF0B7 6. Removal of stain is a part of the process but, complete removal of stains cannot be\n" +
                "guaranteed and will be processed at customer&#39;s risk.\n" + "<br>" + "<br>" +
                "\uF0B7 7. Company is not responsible for loss or damage to any personal or non-cleanable\n" +
                "items left in the article such as money, jewellery, or anything else.\n" + "<br>" + "<br>" +
                "\uF0B7 8. If customer cannot accept the loss of any garment, please do not leave it with us.\n" + "<br>" + "<br>" +
                "\uF0B7 9. In case of any loss or damage Company can reimburse up to a maximum of 5 times\n" +
                "of its processing (laundry / dryclean) cost (decision remains with Company if any\n" +
                "reimbursement has to be done) only if Customer is able to produce the bills.\n" +
                "Compensation shall be provided in form of services and no cash will be given to\n" +
                "customer.\n" +
                "\n" + "<br>" + "<br>" +
                "\uF0B7 10. Customers are requested to count the articles at the time of delivery and inform the\n" +
                "delivery man in case of missing articles. Company will not be responsible for any such\n" +
                "claims after the articles delivery has been accepted and signed by the customer.\n" + "<br>" + "<br>" +
                "\uF0B7 11. Customer gives a warranty of maximum upto 3 days from the date of delivery for the\n" +
                "articles for any quality related issues with washing or dry-cleaning of articles (only if the\n" +
                "article has not been used by the customer after service). Any claim after the stipulated\n" +
                "time shall not be entertained.\n" + "<br>" + "<br>" +
                "\uF0B7 12. Customer might get regular updates (Calls / SMS / Email / App Notification) from\n" +
                "Company of transactional and marketing in nature, if customer wants to stop it then\n" +
                "Customer has to register a request with Customer Care Department of Company.\n" + "<br>" + "<br>" +
                "\uF0B7 13. Company shall not be held responsible for any ornaments/ jewellery fittings on the\n" +
                "garment.\n" + "<br>" + "<br>" +
                "\uF0B7 14. Customer shall examine articles for damage at the time of delivery, and notify the\n" +
                "same at that time. Company shall not be responsible for any claims afterwards.\n" + "<br>" + "<br>" +
                "\uF0B7 15. Any loss/damage/delay due to FORCE MAJEURE conditions, Company is not liable\n" +
                "for any compensation or reduction in charges.\n" + "<br>" + "<br>" +
                "\uF0B7 16. We accept no liability for any loss or damage of the articles arising due to fire,\n" +
                "burglary etc. beyond conduct or any other similar unforseen causes.\n" + "<br>" + "<br>" +
                "\uF0B7 17. Some process/items may require additional period to process. No deduction on\n" +
                "billed amount or claim can be initiated against in respect of delays.\n" + "<br>" + "<br>" +
                "\uF0B7 18. Tariff for designer wear will be decided on a case to case to case basis depending\n" +
                "on the complexity of garment, the same shall be communicated to customer after it is\n" +
                "examined by the expert at the processing centre.\n" +
                "\n" + "<br>" + "<br>" + "<b>" +
                "Payment Terms:\n" + "</b>" + "<br>" + "<br>" +
                "\uF0B7 1. Company does not provide printed bills, and invoices will be sent by e-mail only on\n" +
                "registered email id.\n" + "<br>" + "<br>" +
                "\uF0B7 2. Customer will be charged twice the amount of regular order for any express delivery.\n" + "<br>" + "<br>" +
                "\uF0B7 3. Customer has to pay the entire amount after successful delivery of order.\n" + "<br>" + "<br>" +
                "\uF0B7 4. For retail orders, customer has to pay by cash only, we accept debit or credit cards as\n" +
                "well, but it is applicable during booking time and its depends on customer desire.\n" + "<br>" + "<br>" +
                "\uF0B7 5. Tax will be charged over and above the rates mentioned in the website or Mobile App\n" + "<br>" + "<br>" +
                "“OMI App”.\n" +
                "\n" + "<br>" + "<br>" + "<b>" +
                "Cancellation / Refund Policy:\n" + "</b>" + "<br>" + "<br>" +
                "\uF0B7 1. User can cancel at any time before actual pick-up. No cancellation charges will be\n" +
                "levied.\n" + "<br>" + "<br>" +
                "\uF0B7 2. In case company deny to process any article because of any reason, the order\n" +
                "amount for that article will be refunded to retail customer in case of online payment.\n" + "<br>" + "<br>" +
                "\uF0B7 3. Once order is placed and picked up , order can&#39;t be cancelled.\n" +
                "\n" + "<br>" + "<br>" + "<b>" +
                "Coupons &amp; Discounts:\n" + "</b>" + "<br>" + "<br>" +
                "\uF0B7 1. Coupon can only be applied by customer using OMI mobile app on available platform.\n" + "<br>" + "<br>" +
                "\uF0B7 2. Once order placed and invoice is generated coupon can&#39;t be applied.\n" + "<br>" + "<br>" + "<b>" +
                "Privacy Policy:\n" + "</b>" + "<br>" + "<br>" +
                "\uF0B7 1. Company never sell your personal information like phone number, address or email id\n" +
                "with any third party.\n" + "<br>" + "<br>" +
                "\uF0B7 2. Company may hire other companies to provide limited administrative and\n" +
                "communication services on companies’ behalf, such as hosting sites, the processing and\n" +
                "delivery of mailings, providing customer support, or providing credit card processing\n" +
                "services. Company has the right to disclose your Personal Information to these third\n" +
                "party companies for the purpose of administering and maintaining the Site and App&#39;s\n" +
                "features, functions and operations. Those companies will be permitted to obtain only\n" +
                "such Personal Information as necessary for them to deliver the respective services, and\n" +
                "we do not authorize these companies to use your Personal Information except for the\n" +
                "sole purpose of providing those services requested by us.\n" +
                "\n" + "<br>" + "<br>" + "<b>" +
                "Links To Other Sites:\n" + "</b>" + "<br>" + "<br>" +
                "\uF0B7 1. Company doesn&#39;t hold any liability on account of damages occurred of any kind from\n" +
                "the content published on our website.\n" + "<br>" + "<br>" + "<b>" +
                "Distribution of Information:\n" + "</b>" + "<br>" + "<br>" +
                "\uF0B7 1. The content of this website is an intellectual property of Company. Any distribution\n" +
                "whatsoever will not be tolerated and shall be punishable by law.\n" +
                "\n" + "<br>" + "<br>" + "<b>" +
                "Prohibited Uses of Site:\n" + "</b>" + "<br>" + "<br>" +
                "\uF0B7 1. Impersonating or imitating as someone else shall be liable for legal action accordingly.\n" +
                "\n" + "<br>" + "<br>" + "<b>" +
                "Content Permission &amp; Restrictions:\n" + "</b>" + "<br>" + "<br>" +
                "\uF0B7 1. Any imitation or distribution of content on this website for any purpose shall be treated\n" +
                "as infringement and shall be punishable by law. Publishing of any third party content is\n" +
                "prohibited and shall be liable for legal action.\n" +
                "\n" + "<br>" + "<br>" + "<b>" +
                "Changes to the Agreement:\n" + "</b>" + "<br>" + "<br>" +
                "\n" +
                "\uF0B7 1. The above mentioned information is liable to change at any time without any prior\n" +
                "notice. Company shall not be liable for any loss incurred.\n" +
                "\n" + "<br>" + "<br>" +
                "All disputes are subject to jurisdiction of courts\n" + "<br>" + "<br>" +
                "For any complaint / query please contact us at support@omionline.in";
    }

    public void backimage(View view) {
        finish();
    }

    public void rest(View view) {
        rest.setVisibility(View.VISIBLE);
        landry.setVisibility(View.GONE);
    }

    public void landy(View view) {
        landry.setVisibility(View.VISIBLE);
        rest.setVisibility(View.GONE);
    }
}
