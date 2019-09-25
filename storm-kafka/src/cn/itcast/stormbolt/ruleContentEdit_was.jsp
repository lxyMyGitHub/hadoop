<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<c:if test="${isChildPage!=1 }">
	<%@ include file="../common/head.jsp" %>
	<%@ include file="../common/scripts.jsp" %>
</c:if>
<style>
@media (min-width: 1368px) {
	.form-control {
	  display:inline;
	  height: 33px;
	  padding: 6px 12px;
	  font-size: 14px;
	  line-height: 1.42857143;
	  color: #333333;
	  background-color: #fff;
	  background-image: none;
	  border: 1px solid #AAAAAA;
	  border-radius: 0px;
	  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
	       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
	          transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
	}
}

@media (max-width: 1367px) {
	.form-control {
	  display:inline;
	  height: 30px;
	  padding: 4px 10px 6px 10px;
	  font-size: 14px;
	  line-height: 1.42857143;
	  color: #333333;
	  background-color: #fff;
	  background-image: none;
	  border: 1px solid #AAAAAA;
	  border-radius: 0px;
	  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
	       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
	          transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
	}
}

.control-label {
	width: 130px;
	padding-right: 10px;
}

.conditionSelectWidth{
	width: 120px;
}
</style>
<div class="rightContent-padding-bottom">
	<div class="bg-white" id="editRuleContentBgWhite">
		<div style="padding-top:20px;padding-left:20px;">
			<form class="form-horizontal" role="form" id="ruleEditForm" method="post">
			    <input type="hidden" id="path" value="${path}"/>
			    <input type="hidden" id="isInternal" value="${isInternal}">
			    <input type="hidden" id="isChildPage" value="${isChildPage}">
			    <input type="hidden" id="serialNo" name="serialNo" value="${serialNo}">
			    <input type="hidden" id="ruleType" name="ruleType" value="${ruleContent.ruleType}"/>
			    <input type="hidden" id="baseFeaturesJson" name="baseFeaturesJson" value="${fn:replace(baseFeaturesJson, '\"', '&quot;')}" />
				<input type="hidden" id="baseFeatureNoTypeMapJson" name="baseFeatureNoTypeMapJson" value="${fn:replace(baseFeatureNoTypeMapJson, '\"', '&quot;')}" />
			    <input type="hidden" id="statisFeaturesJson" name="statisFeaturesJson" value="${fn:replace(statisFeaturesJson, '\"', '&quot;')}" />
			    <input type="hidden" id="correlateFeaturesJson" name="correlateFeaturesJson" value="${fn:replace(correlateFeaturesJson, '\"', '&quot;')}" />
			    <input type="hidden" id="dictionarysJson" name="dictionarysJson" value="${fn:replace(dictionarysJson, '\"', '&quot;')}" /> 
			    <input type="hidden" id="businessDicMap" value="${fn:replace(businessDicMap, '\"', '&quot;')}"/>
                 <input type="hidden" id="redisSetMapJson" name="redisSetMapJson" value="${fn:replace(redisSetMapJson, '\"', '&quot;')}"/> 
			    <div class="form-group row">
					<label for="ruleCode" class="control-label"><fmt:message>ruleContent_2</fmt:message></label>
					<input type="text"
						class="form-control floatInline validate[required,minSize[6],maxSize[50],custom[ruleCode]]"
						style="width:400px;" id="ruleCode" name="ruleCode" value="${ruleContent.ruleCode}"
						data-prompt-position="inline" data-prompt-target="ruleCode_target" readonly>
					<span class="inputCaption" id="ruleCode_target"> <i
						class="star">*</i>&nbsp; <fmt:message>msg_15</fmt:message>
					</span>
				</div>
				<div class="form-group row">
					<label for="ruleName" class="control-label"><fmt:message>ruleContent_3</fmt:message></label>
					<input type="text"
						class="formElement form-control floatInline validate[required,maxSize[50],custom[featureName]]"
						style="width:400px;" id="ruleName" name="ruleName" value="${ruleContent.ruleName}"
						data-prompt-position="inline" data-prompt-target="ruleName_target">
					<span class="inputCaption" id="ruleName_target"> <i class="star">*</i>&nbsp; <fmt:message>msg_16</fmt:message>
					</span>
				</div>
				<div class="form-group row">
					<label for="salienceScore" class="control-label"><fmt:message>优先级分值</fmt:message></label>
					<input type="text"
						class="formElement form-control floatInline validate[required,custom[salienceScore]]"
						style="width:400px;" id="salienceScore" name="salienceScore" value="${ruleContent.salienceScore}"
						data-prompt-position="inline" data-prompt-target="salienceScore_target">
					<span class="inputCaption" id="salienceScore_target"> <i
						class="star">*</i>&nbsp;<fmt:message> 请输入0-99之间的正整数，数值越大则优先级越高</fmt:message>
					</span>
				</div>
				<div class="form-group row">
					<label for="score" class="control-label"><fmt:message>ruleContent_6</fmt:message></label>
					<input type="text"
						class="formElement form-control floatInline validate[required,custom[riskScore]]"
						style="width:400px;" id="score" name="score" value="${ruleContent.score}"
						data-prompt-position="inline" data-prompt-target="score_target">
					<span class="inputCaption" id="score_target"> 
					  <i class="star">*</i>&nbsp;<fmt:message>msg_17</fmt:message>
					</span>
				</div>
				
				<div class="form-group row">
					<label for="description" class="control-label"><fmt:message>ruleContent_5</fmt:message></label>
					<textarea class="formElement form-control floatInline validate[maxSize[1000]]"
						id="description" name="description" style="width:70%;"
						data-prompt-position="inline"
						data-prompt-target="description_target" >${ruleContent.description}</textarea>
				</div>
				
				<!-- 规则条件 Start-->
				<div class="form-group row">
					<label class="control-label "><fmt:message>ruleContent_8</fmt:message></label>
					<div>
						<a id="addRuleCondition" data-duplicate-for="ruleCondition"
							data-duplicate-max="11" class="btn icon-padding"> 
							<span class="img-addXIcon"><fmt:message>rtool_5</fmt:message></span>
						</a>				
					</div>
					<div id="ruleConditions">
						<div data-duplicate-name="ruleCondition" style="display:none;">
							<div style="display:inline-block">
							    <table id="table-0" class="conditonTable" width="100%;">
							    	<tr>
							    		<td class="conditionSelectWidth">
							    			<select name="conditionType" id="0-conditionType"
												onchange="conditionTypeChange(this);" class="conditionSelectWidth">
												<option value="">选择条件类型</option>
												<option value="1">单一条件</option>
												<option value="2">组合条件</option>
											</select>
							    		</td>
							    		<td >
							    			<div id="0-conditionDiv" style="padding-left:10px;"></div>
							    		</td>
							    		<td style="width:100px;">
							    			<a data-duplicate-remove href="#" class="btn icon-padding">
												<span class="img-deleteXIcon"><fmt:message>rtool_6</fmt:message></span>
											</a>
							    		</td>
							    	</tr>
							    </table>
							</div>
						</div>
						<c:forEach items="${ruleConditionGroups}" var="conditionGroup" varStatus="status">
							<div data-duplicate-name="ruleCondition">
								<div style="display:inline-block">
									<!-- 单一条件  -->
									<c:if test="${conditionGroup.conditionType == 1}">
										<table class="conditonTable" width="100%;">
									    	<tr>
									    		<td class="conditionSelectWidth" >
									    			<select name="conditionType" id="${conditionGroup.conditiongroupId}-conditionType"
														onchange="conditionTypeChange(this);" class="conditionSelectWidth">
														<option value="1" selected="selected">单一条件</option>
														<option value="2">组合条件</option>
													</select>
									    		</td>
									    		<td >
									    			<div id="${conditionGroup.conditiongroupId}-conditionDiv" style="padding-left:10px;">
									    			    <input type="hidden" name="conditionGroupDes" id="${conditionGroup.conditiongroupId}-conditionGroupDes" value=" ${conditionGroup.conditiongroupDes}">
									    				<c:set var="tmpCondition" value="${conditionMap[conditionGroup.conditiongroupId]}"></c:set>
									    				<input type="hidden" name="conditionGroupId" id="${tmpCondition.conditionId}-conditionGroupId" value="${conditionGroup.conditiongroupId}"/>
									    				<input type="hidden" name="conditionParameter" id="${conditionGroup.conditiongroupId}-conditionParameter" value="${tmpCondition.conditionParameter}"/>
									    				<c:set var="tmpFeatureClass1" value="${tmpCondition.conParaMap['featureType1']}"></c:set>
									    				<c:set var="tmpFeature1ID" value="${features[tmpCondition.conParaMap['feature1']].attributeID}"></c:set>
														<c:set var="tmpFeature1Type" value="${features[tmpCondition.conParaMap['feature1']].attributeType}"></c:set>
									    				<c:set var="tmpOperatorType1" value="${tmpCondition.conParaMap['operatorType1']}"></c:set>
									    				<c:set var="tmpFeatureClass2" value="${tmpCondition.conParaMap['featureType2']}"></c:set>
									    				<c:set var="tmpFeature2Type" value="${features[tmpCondition.conParaMap['feature2']].attributeType}"></c:set>
									    				<c:set var="tmpOperator1" value="${tmpCondition.conParaMap['operator1']}"></c:set>
														<c:set var="tmpContrastValueType" value="${tmpCondition.conParaMap['contrastValueType']}"></c:set>
														
									    				<select name="featureType1" id="${tmpCondition.conditionId}-featureType1" onchange="hideSetValue('${tmpCondition.conditionId}','1');featureType1Change('${tmpCondition.conditionId}');" style="width:120px;float:left;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['featureType']}" var="type" varStatus="status">
																<option value="${type.key }" <c:if test="${tmpFeatureClass1==type.key}">selected</c:if>>${type.value }</option>
															</c:forEach>
														</select> 
														<select name="feature1" id="${tmpCondition.conditionId}-feature1" onchange="hideSetValue('${tmpCondition.conditionId}','2');feature1Change('${tmpCondition.conditionId}');" class="select-picker validate[required]" data-width="180px" data-size="10" data-dropdown-align-right="true" data-live-search="true" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${featureMapWithClassKey[tmpFeatureClass1]}" var="tmpFeature" varStatus="status">
														    	<option value="${tmpFeature.key }" <c:if test="${tmpCondition.conParaMap['feature1']==tmpFeature.key}">selected</c:if>>${tmpFeature.value.attributeName}</option>
														    </c:forEach>
														</select>
														<select name="numberSetOperator1" id="${tmpCondition.conditionId}-numberSetOperator1" onchange="hideSetValue('${tmpCondition.conditionId}','4');numberSetOperator1Change('${tmpCondition.conditionId}');" style="width:120px;<c:if test="${tmpFeature1Type!='numberSet' }">display:none;</c:if> float:left;margin-left:4px;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['numberSetOperator']}" var="dic" varStatus="status">
																<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['numberSetOperator1']==dic.key}">selected</c:if>>${dic.value}</option>
															</c:forEach>
														</select>
														<select name="operatorType1" id="${tmpCondition.conditionId}-operatorType1" featureType="${tmpFeature1Type}" onchange="hideSetValue('${tmpCondition.conditionId}','3');operatorType1Change('${tmpCondition.conditionId}');" style="width:120px;float:left;margin-left:4px;<c:if test="${null==tmpOperatorType1 || tmpOperatorType1 =='' }">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError">
															<c:set var="tmpOperatorTypeDicKey" value="operatorType${tmpFeature1Type}"></c:set>
															<option value="">请选择</option>
															<c:forEach items="${dictionarys[tmpOperatorTypeDicKey]}" var="dic" varStatus="status">
																<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['operatorType1']==dic.key}">selected</c:if>>${dic.value}</option>
															</c:forEach>
														</select>
														<select name="operator1" id="${tmpCondition.conditionId}-operator1" onchange="operator1Change('${tmpCondition.conditionId}');" style="width:120px;float:left;margin-left:4px;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError" <c:if test="${tmpCondition.conParaMap['operator1'] == null || tmpCondition.conParaMap['operator1'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:if test="${tmpFeature1Type == 'date' && tmpOperatorType1 == '1'}">
																<c:forEach items="${dictionarys['dateComputeOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'date' && tmpOperatorType1 == '2'}">
																<c:forEach items="${dictionarys['dateCompareOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
															<c:if test="${(tmpFeature1Type == 'number'||tmpFeature1Type == 'numberSet') && tmpOperatorType1 == '1'}">
																<c:forEach items="${dictionarys['numberComputeOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'number' && tmpOperatorType1 == '2'}">
																<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																    <c:if test="${dic.key != '%' || (dic.key == '%' && tmpFeatureClass1 == '0') }">
																		<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																	</c:if>
																</c:forEach>
																<c:if test="${tmpCondition.conParaMap['featureType1']=='0'}">
																	<option value="in" <c:if test="${tmpCondition.conParaMap['operator1']=='in'}">selected</c:if>>in</option>
																	<option value="notIn" <c:if test="${tmpCondition.conParaMap['operator1']=='notIn'}">selected</c:if>>not in</option>
																	<option value="startsWith" <c:if test="${tmpCondition.conParaMap['operator1']=='startsWith'}">selected</c:if>>startsWith</option>
																	<option value="endsWith" <c:if test="${tmpCondition.conParaMap['operator1']=='endsWith'}">selected</c:if>>endsWith</option>
																</c:if>
															</c:if>
															<c:if test="${tmpFeature1Type == 'numberSet'&&(null ==tmpOperatorType1 || tmpOperatorType1 =='' ||tmpOperatorType1 == '2')}">
																<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																	<c:if test="${dic.key != '%' || (dic.key == '%' && tmpFeatureClass1 == '0') }">
																		<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																	</c:if>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'cnumberSet' && tmpCondition.conParaMap['operatorType1']=='call'}">
																<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																	<c:if test="${dic.key != '%' || (dic.key == '%' && tmpFeatureClass1 == '0') }">
																		<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																	</c:if>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'string' && tmpOperatorType1 == '2'}">
																<c:forEach items="${dictionarys['stringCompareOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
														</select> 
														<select name="featureType2" id="${tmpCondition.conditionId}-featureType2" onchange="hideSetValue('${tmpCondition.conditionId}','5');featureType2Change('${tmpCondition.conditionId}');" style="width:120px;float:left;margin-left:4px;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError" <c:if test="${tmpCondition.conParaMap['featureType2'] == null || tmpCondition.conParaMap['featureType2'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['featureType']}" var="type" varStatus="status">
																<option value="${type.key }" <c:if test="${tmpFeatureClass2==type.key}">selected</c:if>>${type.value }</option>
															</c:forEach>
														</select> 
														
														<select name="feature2" id="${tmpCondition.conditionId}-feature2" onchange="hideSetValue('${tmpCondition.conditionId}','5');feature2Change('${tmpCondition.conditionId}');" class="select-picker validate[required]" data-width="180px" data-size="10" data-dropdown-align-right="true" data-live-search="true" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError" <c:if test="${tmpCondition.conParaMap['featureType2'] == null || tmpCondition.conParaMap['featureType2'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:forEach items="${featureMapWithClassKey[tmpFeatureClass2]}" var="tmpFeature" varStatus="status">
														    	<c:if test="${(tmpFeatureClass2 == '0' && tmpFeature.value.attributeType!='string') || (tmpFeatureClass2 == '1') || (tmpFeatureClass2 == '2' && tmpFeature.value.attributeType == 'number')}">
														    		<option value="${tmpFeature.key }" <c:if test="${tmpCondition.conParaMap['feature2']==tmpFeature.key}">selected</c:if>>${tmpFeature.value.attributeName}</option>
														    	</c:if>
														    </c:forEach>
														</select>
														<select name="numberSetOperator2" id="${tmpCondition.conditionId}-numberSetOperator2" style="width:120px;float:left;margin-left:4px;<c:if test="${tmpFeature2Type!='numberSet' }">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['numberSetOperator']}" var="dic" varStatus="status">
															    <c:if test="${dic.key!='all' && dic.key!='incre' && dic.key!='decre'}">
																	<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['numberSetOperator2']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:if>
															</c:forEach>
														</select>

														<select name="operator2" id="${tmpCondition.conditionId}-operator2" onchange="operator2Change('${tmpCondition.conditionId}');" style="width:120px;float:left;margin-left:4px;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError" <c:if test="${tmpCondition.conParaMap['operator2'] == null || tmpCondition.conParaMap['operator2'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
															    <c:if test="${dic.key != '%'}">
																	<option value="${dic.key}" <c:if test="${tmpCondition.conParaMap['operator2']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:if>
															</c:forEach>
														</select> 
														<select name="contrastValueTypes" id="${tmpCondition.conditionId}-contrastValueType" onchange="contrastValueTypeChange('${tmpCondition.conditionId}');" style="width:120px;float:left;margin-left:4px;<c:if test="${tmpCondition.conParaMap['contrastValueType']=='' || tmpCondition.conParaMap['contrastValueType'] == null}">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:if test="${tmpCondition.conParaMap['featureType1']=='0' && tmpFeature1Type == 'string' && (tmpOperator1 == '=='||tmpOperator1 =='!='||tmpOperator1=='in'||tmpOperator1=='notIn') &&null != businessDic[tmpFeature1ID]}">
																<option value="2" <c:if test="${tmpCondition.conParaMap['contrastValueType']=='2'}">selected</c:if>>字典项</option>
															</c:if>
															<c:if test="${tmpFeature1Type != 'date' }">
															    <option value="0" <c:if test="${tmpContrastValueType =='0'}">selected</c:if>>输入值</option>
															</c:if>
															<c:if test="${tmpCondition.conParaMap['featureType1']=='0' && ((tmpFeature1Type == 'string' && (tmpOperator1 == '=='||tmpOperator1 =='!='))|| tmpFeature1Type == 'date')}">
																<option value="1" <c:if test="${tmpContrastValueType =='1'}">selected</c:if>>基本特征</option>
															</c:if>
															<c:if test="${tmpCondition.conParaMap['featureType1']=='0' && null != redisSetMap[tmpFeature1ID] }">
																<option value="3" <c:if test="${tmpContrastValueType =='3'}">selected</c:if>>集合</option>
															</c:if>
														</select>
														<div id="${tmpCondition.conditionId}-contrastValueDiv" class="contrastValueDiv" style="display:inline-block;float:left;margin-left:4px;">
															<c:if test="${null == tmpContrastValueType || tmpContrastValueType == '' || tmpContrastValueType == '0' }">
																<c:choose>
																	<c:when test="${fn:contains(tmpFeature1Type,'number') && tmpOperator1 != '%' && tmpOperator1 != 'in' && tmpOperator1 != 'notIn' && tmpOperator1 != 'startsWith'&&tmpOperator1!='endsWith'}">
																		<input type="text" class="formElement form-control validate[required,maxSize[50],custom[positivenumber]]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError" value="${tmpCondition.conParaMap['contrastValue'] }"
																				style="width:200px;" onchange="contrastValueChange('${tmpCondition.conditionId}')" id="${tmpCondition.conditionId}-contrastValue" name="contrastValue" <c:if test="${tmpCondition.conParaMap['contrastValue'] == null || tmpCondition.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	</c:when>
																	<c:when test="${fn:contains(tmpFeature1Type,'number') && tmpOperator1 == '%'}">
																		<input type="text" class="formElement form-control validate[required,maxSize[50],custom[positiveInteger]]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError" value="${tmpCondition.conParaMap['contrastValue'] }"
																				style="width:200px;" onchange="contrastValueChange('${tmpCondition.conditionId}')" id="${tmpCondition.conditionId}-contrastValue" name="contrastValue" <c:if test="${tmpCondition.conParaMap['contrastValue'] == null || tmpCondition.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	</c:when>
																	<c:otherwise>
																		<input type="text" class="formElement form-control validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError" value="${tmpCondition.conParaMap['contrastValue'] }"
																		style="width:200px;" onchange="contrastValueChange('${tmpCondition.conditionId}')" id="${tmpCondition.conditionId}-contrastValue" name="contrastValue" <c:if test="${tmpCondition.conParaMap['contrastValue'] == null || tmpCondition.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	</c:otherwise>
																</c:choose>
															</c:if>
															<c:if test="${tmpContrastValueType == '1' }"><!-- 基本特征 -->
																<select class="formElement validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError"
																        style="width:200px;" onchange="contrastValueChange('${tmpCondition.conditionId}')" id="${tmpCondition.conditionId}-contrastValue" name="contrastValue" >
																	<option value="">请选择</option>
																	<c:if test="${fn:contains(tmpFeature1Type,'number')}">
																		<c:forEach items="${baseFeatureNoTypeMap}" var="baseFeature" varStatus="status">
																			<c:if test="${ baseFeature.value.attributeType == 'number'}">
																				<option value="${baseFeature.key}" <c:if test="${tmpCondition.conParaMap['contrastValue'] == baseFeature.key}">selected</c:if>>${baseFeature.value.attributeName}</option>
																			</c:if>
																		</c:forEach>
																	</c:if>
																	<c:if test="${tmpFeature1Type == 'date'}">
																		<c:forEach items="${baseFeatureNoTypeMap}" var="dateFeature" varStatus="status">
																			<c:if test="${ dateFeature.value.attributeType == 'date'}">
																				<option value="${dateFeature.key}" <c:if test="${tmpCondition.conParaMap['contrastValue'] == dateFeature.key}">selected</c:if>>${dateFeature.value.attributeName}</option>
																			</c:if>
																		</c:forEach>
																	</c:if>
																	<c:if test="${tmpFeature1Type == 'string'}">
																		<c:forEach items="${baseFeatureNoTypeMap}" var="tmp" varStatus="status">
																				<option value="${tmp.key}" <c:if test="${tmpCondition.conParaMap['contrastValue'] == tmp.key}">selected</c:if>>${tmp.value.attributeName}</option>
																		</c:forEach>
																	</c:if>
																</select>
															</c:if>
															<c:if test="${tmpContrastValueType == '2' }"><!-- 字典项 -->
																<c:if test="${tmpCondition.conParaMap['featureType1']=='0' &&(tmpOperator1 == '=='||tmpOperator1 =='!='||tmpOperator1=='in'||tmpOperator1=='notIn') &&null != businessDic[tmpFeature1ID] }">
																	<select class="formElement validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError"
																	        style="width:200px;" onchange="contrastValueChange('${tmpCondition.conditionId}')" id="${tmpCondition.conditionId}-contrastValue" name="contrastValue" 
																	        <c:if test="${tmpOperator1 == 'in'|| tmpOperator1 == 'notIn'}"> multiple = "multiple"</c:if>
																	        <c:if test="${tmpCondition.conParaMap['contrastValue'] == null || tmpCondition.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																		<option value="">请选择</option>
																		<c:forEach items="${businessDic[tmpFeature1ID]}" var="dic" varStatus="status">
																			<option value="${dic.key}" <c:if test="${fn:contains(tmpCondition.conParaMap['contrastValue'],dic.key)}">selected</c:if>>${dic.value}</option>
																		</c:forEach>
																	</select>
																</c:if>
															</c:if>
															<c:if test="${tmpContrastValueType == '3' && null != redisSetMap[tmpFeature1ID]}"><!-- Redis集合 -->
																<select class="formElement validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition.conditionId}-conditionDivError"
																        style="width:200px;" onchange="contrastValueChange('${tmpCondition.conditionId}')" id="${tmpCondition.conditionId}-contrastValue" name="contrastValue" 
																        <c:if test="${tmpCondition.conParaMap['contrastValue'] == null || tmpCondition.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	<option value="">请选择</option>
																	<c:forEach items="${redisSetMap[tmpFeature1ID]}" var="entry" varStatus="status">
																		<option value="${entry.key}" <c:if test="${tmpCondition.conParaMap['contrastValue'] == entry.key}">selected</c:if>>${entry.value}</option>
																	</c:forEach>
																</select>
															</c:if>
															
														</div>
									    			</div>
									    		</td>
									    		<td >
									    			<div id="${tmpCondition.conditionId}-conditionDivError"></div>
									    		</td>
									    		<td style="width:100px;">
									    			<a data-duplicate-remove href="#" class="btn icon-padding">
														<span class="img-deleteXIcon"><fmt:message>rtool_6</fmt:message></span>
													</a>
									    		</td>
									    	</tr>
									    </table>
									</c:if>
									<!-- 单一条件  End-->
									<!-- 组合条件  -->
									<c:if test="${conditionGroup.conditionType == 2}">
										<table class="conditonTable" width="100%;">
									    	<tr>
									    		<td class="conditionSelectWidth" valign ="top">
									    			<select name="conditionType" id="${conditionGroup.conditiongroupId}-conditionType"
														onchange="conditionTypeChange(this);" class="conditionSelectWidth">
														<option value="1" >单一条件</option>
														<option value="2" selected="selected">组合条件</option>
													</select>
									    		</td>
									    		<td >
									    			<input type="hidden" name="conditionGroupDes" id="${conditionGroup.conditiongroupId}-conditionGroupDes" value=" ${conditionGroup.conditiongroupDes}">
								    				<c:set var="tmpConditionId1" value="${conditionGroup.conditiongroupId}1"></c:set>
								    				<c:set var="tmpCondition1" value="${conditionMap[tmpConditionId1]}"></c:set>
								    				<input type="hidden" name="uuid" id="${conditionGroup.conditiongroupId}-uuid" value="${conditionGroup.conditiongroupId}">
								    				<input type="hidden" name="uuid1" id="${conditionGroup.conditiongroupId}-uuid1" value="${tmpConditionId1}">
								    				<input type="hidden" name="conditionGroupId" id="${tmpCondition1.conditionId}-conditionGroupId" value="${conditionGroup.conditiongroupId}"/>
									    			<c:set var="tmpFeatureClass11" value="${tmpCondition1.conParaMap['featureType1']}"></c:set>
									    			<c:set var="tmpFeature1ID" value="${features[tmpCondition1.conParaMap['feature1']].attributeID}"></c:set>
													<c:set var="tmpFeature1Type" value="${features[tmpCondition1.conParaMap['feature1']].attributeType}"></c:set>
									    			<c:set var="tmpOperatorType1" value="${tmpCondition1.conParaMap['operatorType1']}"></c:set>
									    			<c:set var="tmpFeatureClass21" value="${tmpCondition1.conParaMap['featureType2']}"></c:set>
									    			<c:set var="tmpFeature2Type" value="${features[tmpCondition1.conParaMap['feature2']].attributeType}"></c:set>
                                                                                                <c:set var="tmpOperator1" value="${tmpCondition1.conParaMap['operator1']}"></c:set>
											        <c:set var="tmpContrastValueType" value="${tmpCondition1.conParaMap['contrastValueType']}"></c:set>
									    			
									    			<div id="${tmpConditionId1}-conditionDiv" style="padding-left:10px;display:inline-block;">
									    				<input type="hidden" name="conditionParameter" id="${tmpConditionId1}-conditionParameter" value="${tmpCondition1.conditionParameter}"/>
									    				<select name="featureType1" id="${tmpCondition1.conditionId}-featureType1" onchange="hideSetValue('${tmpCondition1.conditionId}','1');featureType1Change('${tmpCondition1.conditionId}');" style="width:120px;float:left;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['featureType']}" var="type" varStatus="status">
																<option value="${type.key }" <c:if test="${tmpFeatureClass11==type.key}">selected</c:if>>${type.value }</option>
															</c:forEach>
														</select> 
														<select name="feature1" id="${tmpCondition1.conditionId}-feature1" onchange="hideSetValue('${tmpCondition1.conditionId}','2');feature1Change('${tmpCondition1.conditionId}');" class="select-picker validate[required]" data-width="180px" data-size="10" data-dropdown-align-right="true" data-live-search="true"  data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError">
															<option value="">请选择</option>
														    <c:forEach items="${featureMapWithClassKey[tmpFeatureClass11]}" var="tmpFeature" varStatus="status">
														    	<option value="${tmpFeature.key }" <c:if test="${tmpCondition1.conParaMap['feature1']==tmpFeature.key}">selected</c:if>>${tmpFeature.value.attributeName}</option>
														    </c:forEach>
														</select>
														<select name="numberSetOperator1" id="${tmpCondition1.conditionId}-numberSetOperator1" onchange="hideSetValue('${tmpCondition1.conditionId}','4');numberSetOperator1Change('${tmpCondition1.conditionId}');" style="width:120px;float:left;margin-left:4px;<c:if test="${tmpFeature1Type!='numberSet' }">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['numberSetOperator']}" var="dic" varStatus="status">
																<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['numberSetOperator1']==dic.key}">selected</c:if>>${dic.value}</option>
															</c:forEach>
														</select>
														
														<select name="operatorType1" id="${tmpCondition1.conditionId}-operatorType1" featureType="${tmpFeature1Type}" onchange="hideSetValue('${tmpCondition1.conditionId}','3');operatorType1Change('${tmpCondition1.conditionId}');" style="width:120px;float:left;margin-left:4px;<c:if test="${null==tmpOperatorType1 || tmpOperatorType1 =='' }">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError">
															<c:set var="tmpOperatorTypeDicKey" value="operatorType${tmpFeature1Type}"></c:set>
															<option value="">请选择</option>
															<c:forEach items="${dictionarys[tmpOperatorTypeDicKey]}" var="dic" varStatus="status">
																<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['operatorType1']==dic.key}">selected</c:if>>${dic.value}</option>
															</c:forEach>
														</select>
														<select name="operator1" id="${tmpCondition1.conditionId}-operator1" onchange="operator1Change('${tmpCondition1.conditionId}');" style="width:120px;float:left;margin-left:4px;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError" <c:if test="${tmpCondition1.conParaMap['operator1'] == null || tmpCondition1.conParaMap['operator1'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:if test="${tmpFeature1Type == 'date' && tmpOperatorType1 == '1'}">
																<c:forEach items="${dictionarys['dateComputeOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'date' && tmpOperatorType1 == '2'}">
																<c:forEach items="${dictionarys['dateCompareOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
															<c:if test="${(tmpFeature1Type == 'number'||tmpFeature1Type == 'numberSet') && tmpOperatorType1 == '1'}">
																<c:forEach items="${dictionarys['numberComputeOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'number' && tmpOperatorType1 == '2'}">
																<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																	<c:if test="${dic.key != '%' || (dic.key == '%' && tmpFeatureClass11 == '0') }">
																		<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																	</c:if>
																</c:forEach>
																<c:if test="${tmpCondition1.conParaMap['featureType1']=='0'}">
																	<option value="in" <c:if test="${tmpCondition1.conParaMap['operator1']=='in'}">selected</c:if>>in</option>
																	<option value="notIn" <c:if test="${tmpCondition1.conParaMap['operator1']=='notIn'}">selected</c:if>>not in</option>
																	<option value="startsWith" <c:if test="${tmpCondition1.conParaMap['operator1']=='startsWith'}">selected</c:if>>startsWith</option>
																	<option value="endsWith" <c:if test="${tmpCondition1.conParaMap['operator1']=='endsWith'}">selected</c:if>>endsWith</option>
																</c:if>
															</c:if>
															<c:if test="${tmpFeature1Type == 'numberSet'&&(null ==tmpOperatorType1 || tmpOperatorType1 =='' ||tmpOperatorType1 == '2')}">
																<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																	<c:if test="${dic.key != '%' || (dic.key == '%' && tmpFeatureClass11 == '0') }">
																		<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																	</c:if>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'cnumberSet' && tmpCondition1.conParaMap['operatorType1']=='call'}">
																<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																	<c:if test="${dic.key != '%' || (dic.key == '%' && tmpFeatureClass11 == '0') }">
																		<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																	</c:if>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'string' && tmpOperatorType1 == '2'}">
																<c:forEach items="${dictionarys['stringCompareOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
														</select> 
														<select name="featureType2" id="${tmpCondition1.conditionId}-featureType2" onchange="hideSetValue('${tmpCondition1.conditionId}','5');featureType2Change('${tmpCondition1.conditionId}');" style="width:120px;float:left;margin-left:4px;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError" <c:if test="${tmpCondition1.conParaMap['featureType2'] == null || tmpCondition1.conParaMap['featureType2'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['featureType']}" var="type" varStatus="status">
																<option value="${type.key }" <c:if test="${tmpFeatureClass21==type.key}">selected</c:if>>${type.value }</option>
															</c:forEach>
														</select> 
														
														<select name="feature2" id="${tmpCondition1.conditionId}-feature2" onchange="hideSetValue('${tmpCondition1.conditionId}','5');feature2Change('${tmpCondition1.conditionId}');" data-width="180px" class="select-picker validate[required]" data-size="10" data-dropdown-align-right="true" data-live-search="true"  data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError" <c:if test="${tmpCondition1.conParaMap['featureType2'] == null || tmpCondition1.conParaMap['featureType2'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:forEach items="${featureMapWithClassKey[tmpFeatureClass21]}" var="tmpFeature" varStatus="status">
														    	<c:if test="${(tmpFeatureClass21 == '0' && tmpFeature.value.attributeType!='string') || (tmpFeatureClass21 == '1') || (tmpFeatureClass21 == '2' && tmpFeature.value.attributeType == 'number')}">
														    		<option value="${tmpFeature.key }" <c:if test="${tmpCondition1.conParaMap['feature2']==tmpFeature.key}">selected</c:if>>${tmpFeature.value.attributeName}</option>
														    	</c:if>
														    </c:forEach>
														</select>
														<select name="numberSetOperator2" id="${tmpCondition1.conditionId}-numberSetOperator2" style="width:120px;float:left;margin-left:4px;<c:if test="${tmpFeature2Type!='numberSet' }">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['numberSetOperator']}" var="dic" varStatus="status">
																<c:if test="${dic.key!='all'&& dic.key!='incre' && dic.key!='decre'}">
																	<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['numberSetOperator2']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:if>
															</c:forEach>
														</select>
														
														<select name="operator2" id="${tmpCondition1.conditionId}-operator2" onchange="operator2Change('${tmpCondition1.conditionId}');" style="width:120px;float:left;margin-left:4px;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError" <c:if test="${tmpCondition1.conParaMap['operator2'] == null || tmpCondition1.conParaMap['operator2'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																<c:if test="${dic.key != '%'}">
																	<option value="${dic.key}" <c:if test="${tmpCondition1.conParaMap['operator2']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:if>
															</c:forEach>
														</select> 
                                                        <select name="contrastValueTypes" id="${tmpCondition1.conditionId}-contrastValueType" onchange="contrastValueTypeChange('${tmpCondition1.conditionId}');" style="width:120px;float:left;margin-left:4px;<c:if test="${tmpCondition1.conParaMap['contrastValueType']=='' || tmpCondition1.conParaMap['contrastValueType'] == null}">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:if test="${tmpCondition1.conParaMap['featureType1']=='0' && tmpFeature1Type == 'string' && (tmpOperator1 == '=='||tmpOperator1 =='!='||tmpOperator1=='in'||tmpOperator1=='notIn') &&null != businessDic[tmpFeature1ID]}">
																<option value="2" <c:if test="${tmpCondition1.conParaMap['contrastValueType']=='2'}">selected</c:if>>字典项</option>
															</c:if>
															<c:if test="${tmpFeature1Type != 'date' }">
																<option value="0" <c:if test="${tmpContrastValueType =='0'}">selected</c:if>>输入值</option>
															</c:if>
															<c:if test="${tmpCondition1.conParaMap['featureType1']=='0' && ((tmpFeature1Type == 'string' && (tmpOperator1 == '=='||tmpOperator1 =='!='))||tmpFeature1Type == 'date') }">
																<option value="1" <c:if test="${tmpContrastValueType =='1'}">selected</c:if>>基本特征</option>
															</c:if>
															<c:if test="${tmpCondition1.conParaMap['featureType1']=='0' && null != redisSetMap[tmpFeature1ID] }">
																<option value="3" <c:if test="${tmpContrastValueType =='3'}">selected</c:if>>集合</option>
															</c:if>
														</select>
														<div id="${tmpCondition1.conditionId}-contrastValueDiv" class="contrastValueDiv" style="display:inline-block;float:left;margin-left:4px;">
															<c:if test="${null == tmpContrastValueType || tmpContrastValueType == '' || tmpContrastValueType == '0' }">
																<c:choose>
																	<c:when test="${fn:contains(tmpFeature1Type,'number') && tmpOperator1 != '%' && tmpOperator1!='in' && tmpOperator1!='notIn' && tmpOperator1!='startsWith'&&tmpOperator1!='endsWith'}">
																		<input type="text" class="formElement form-control validate[required,maxSize[50],custom[positivenumber]]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError" value="${tmpCondition1.conParaMap['contrastValue'] }"
																				style="width:200px;" onchange="contrastValueChange('${tmpCondition1.conditionId}')" id="${tmpCondition1.conditionId}-contrastValue" name="contrastValue" <c:if test="${tmpCondition1.conParaMap['contrastValue'] == null || tmpCondition1.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	</c:when>
																	<c:when test="${fn:contains(tmpFeature1Type,'number') && tmpOperator1 == '%'}">
																		<input type="text" class="formElement form-control validate[required,maxSize[50],custom[positiveInteger]]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError" value="${tmpCondition1.conParaMap['contrastValue'] }"
																				style="width:200px;" onchange="contrastValueChange('${tmpCondition1.conditionId}')" id="${tmpCondition1.conditionId}-contrastValue" name="contrastValue" <c:if test="${tmpCondition1.conParaMap['contrastValue'] == null || tmpCondition1.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	</c:when>
																	<c:otherwise>
																		<input type="text" class="formElement form-control validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError" value="${tmpCondition1.conParaMap['contrastValue'] }"
																		style="width:200px;" onchange="contrastValueChange('${tmpCondition1.conditionId}')" id="${tmpCondition1.conditionId}-contrastValue" name="contrastValue" <c:if test="${tmpCondition1.conParaMap['contrastValue'] == null || tmpCondition1.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	</c:otherwise>
																</c:choose>
															</c:if>
															<c:if test="${tmpContrastValueType == '1' }"><!-- 基本特征 -->
																<select class="formElement validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError"
																        style="width:200px;" onchange="contrastValueChange('${tmpCondition1.conditionId}')" id="${tmpCondition1.conditionId}-contrastValue" name="contrastValue" >
																	<option value="">请选择</option>
																	<c:if test="${fn:contains(tmpFeature1Type,'number')}">
																		<c:forEach items="${baseFeatureNoTypeMap}" var="baseFeature" varStatus="status">
																			<c:if test="${ baseFeature.value.attributeType == 'number'}">
																				<option value="${baseFeature.key}" <c:if test="${tmpCondition1.conParaMap['contrastValue'] == baseFeature.key}">selected</c:if>>${baseFeature.value.attributeName}</option>
																			</c:if>
																		</c:forEach>
																	</c:if>
																	<c:if test="${tmpFeature1Type == 'date'}">
																		<c:forEach items="${baseFeatureNoTypeMap}" var="dateFeature" varStatus="status">
																			<c:if test="${ dateFeature.value.attributeType == 'date'}">
																				<option value="${dateFeature.key}" <c:if test="${tmpCondition1.conParaMap['contrastValue'] == dateFeature.key}">selected</c:if>>${dateFeature.value.attributeName}</option>
																			</c:if>
																		</c:forEach>
																	</c:if>
																	<c:if test="${tmpFeature1Type == 'string'}">
																		<c:forEach items="${baseFeatureNoTypeMap}" var="tmp" varStatus="status">
																				<option value="${tmp.key}" <c:if test="${tmpCondition1.conParaMap['contrastValue'] == tmp.key}">selected</c:if>>${tmp.value.attributeName}</option>
																		</c:forEach>
																	</c:if>
																</select>
															</c:if>
															<c:if test="${tmpContrastValueType == '2' }"><!-- 字典项 -->
																<c:if test="${tmpCondition1.conParaMap['featureType1']=='0' &&(tmpOperator1 == '=='||tmpOperator1 =='!='||tmpOperator1=='in'||tmpOperator1=='notIn') &&null != businessDic[tmpFeature1ID] }">
																	<select class="formElement validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError"
																	        style="width:200px;" onchange="contrastValueChange('${tmpCondition1.conditionId}')" id="${tmpCondition1.conditionId}-contrastValue" name="contrastValue" 
																	        <c:if test="${tmpOperator1 == 'in'|| tmpOperator1 == 'notIn'}"> multiple = "multiple"</c:if>
																	        <c:if test="${tmpCondition1.conParaMap['contrastValue'] == null || tmpCondition1.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																		<option value="">请选择</option>
																		<c:forEach items="${businessDic[tmpFeature1ID]}" var="dic" varStatus="status">
																			<option value="${dic.key}" <c:if test="${fn:contains(tmpCondition1.conParaMap['contrastValue'],dic.key)}">selected</c:if>>${dic.value}</option>
																		</c:forEach>
																	</select>
																</c:if>
															</c:if>
                                                            <c:if test="${tmpContrastValueType == '3' && null != redisSetMap[tmpFeature1ID]}"><!-- Redis集合 -->
																<select class="formElement validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition1.conditionId}-conditionDivError"
																        style="width:200px;" onchange="contrastValueChange('${tmpCondition1.conditionId}')" id="${tmpCondition1.conditionId}-contrastValue" name="contrastValue" 
																        <c:if test="${tmpCondition1.conParaMap['contrastValue'] == null || tmpCondition1.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	<option value="">请选择</option>
																	<c:forEach items="${redisSetMap[tmpFeature1ID]}" var="entry" varStatus="status">
																		<option value="${entry.key}" <c:if test="${tmpCondition1.conParaMap['contrastValue'] == entry.key}">selected</c:if>>${entry.value}</option>
																	</c:forEach>
																</select>
															</c:if>
															
														</div>
														
									    				<div id="${tmpCondition1.conditionId}-conditionDivError" class="conditionErrorInfo"></div>
									    			</div>
									    			<!-- 组合条件逻辑运算符 Start -->
									    			<div style="padding-left:10px;">
														<select name="logicOperator" id="${conditionGroup.conditiongroupId}-logicOperator" onchange="logicOperatorChange('${conditionGroup.conditiongroupId}');" style="width:120px;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${conditionGroup.conditiongroupId}-conditionDivError">
															<option value="&&" <c:if test="${conditionGroup.logicOperator=='&&'}">selected</c:if>>AND</option>
															<option value="||" <c:if test="${conditionGroup.logicOperator=='||'}">selected</c:if>>OR</option>
														</select>
														<div id="${conditionGroup.conditiongroupId}-conditionDivError" class="conditionErrorInfo"></div> 
													</div>
													<!-- 组合条件逻辑运算符 End -->
													<c:set var="tmpConditionId2" value="${conditionGroup.conditiongroupId}2"></c:set>
								    				<c:set var="tmpCondition2" value="${conditionMap[tmpConditionId2]}"></c:set>
								    				<input type="hidden" name="uuid2" id="${conditionGroup.conditiongroupId}-uuid2" value="${tmpConditionId2}">
								    				<input type="hidden" name="conditionGroupId" id="${tmpCondition2.conditionId}-conditionGroupId" value="${conditionGroup.conditiongroupId}"/>
								    				<c:set var="tmpFeatureClass12" value="${tmpCondition2.conParaMap['featureType1']}"></c:set>
												    <c:set var="tmpFeature1ID" value="${features[tmpCondition2.conParaMap['feature1']].attributeID}"></c:set>
													<c:set var="tmpFeature1Type" value="${features[tmpCondition2.conParaMap['feature1']].attributeType}"></c:set>
													<c:set var="tmpOperatorType1" value="${tmpCondition2.conParaMap['operatorType1']}"></c:set>
													<c:set var="tmpFeatureClass22" value="${tmpCondition2.conParaMap['featureType2']}"></c:set>
													<c:set var="tmpFeature2Type" value="${features[tmpCondition2.conParaMap['feature2']].attributeType}"></c:set>
                                                    <c:set var="tmpOperator1" value="${tmpCondition2.conParaMap['operator1']}"></c:set>
												    <c:set var="tmpContrastValueType" value="${tmpCondition2.conParaMap['contrastValueType']}"></c:set>
														
									    			<div id="${tmpConditionId2}-conditionDiv" style="padding-top:6px;padding-left:10px;display:inline-block;">
									    				<input type="hidden" name="conditionParameter" id="${tmpConditionId2}-conditionParameter" value="${tmpCondition2.conditionParameter}"/>
									    				<select name="featureType1" id="${tmpCondition2.conditionId}-featureType1" onchange="hideSetValue('${tmpCondition2.conditionId}','1');featureType1Change('${tmpCondition2.conditionId}');" style="width:120px;float:left;" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['featureType']}" var="type" varStatus="status">
																<option value="${type.key }" <c:if test="${tmpFeatureClass12==type.key}">selected</c:if>>${type.value }</option>
															</c:forEach>
														</select> 
														<select name="feature1" id="${tmpCondition2.conditionId}-feature1" onchange="hideSetValue('${tmpCondition2.conditionId}','2');feature1Change('${tmpCondition2.conditionId}');" class="select-picker validate[required]"  data-width="180px" data-size="10" data-dropdown-align-right="true" data-live-search="true"  data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${featureMapWithClassKey[tmpFeatureClass12]}" var="tmpFeature" varStatus="status">
														    	<option value="${tmpFeature.key }" <c:if test="${tmpCondition2.conParaMap['feature1']==tmpFeature.key}">selected</c:if>>${tmpFeature.value.attributeName}</option>
														    </c:forEach>
														</select>
														
														<select name="numberSetOperator1" id="${tmpCondition2.conditionId}-numberSetOperator1" onchange="hideSetValue('${tmpCondition2.conditionId}','4');numberSetOperator1Change('${tmpCondition2.conditionId}');" style="width:120px;float:left;margin-left:4px;<c:if test="${tmpFeature1Type!='numberSet' }">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['numberSetOperator']}" var="dic" varStatus="status">
																<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['numberSetOperator1']==dic.key}">selected</c:if>>${dic.value}</option>
															</c:forEach>
														</select>
														<select name="operatorType1" id="${tmpCondition2.conditionId}-operatorType1" featureType="${tmpFeature1Type}" onchange="hideSetValue('${tmpCondition2.conditionId}','3');operatorType1Change('${tmpCondition2.conditionId}');" style="width:120px;float:left;margin-left:4px;<c:if test="${null==tmpOperatorType1 || tmpOperatorType1 =='' }">display:none;</c:if>"  class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError">
                                                            <c:set var="tmpOperatorTypeDicKey" value="operatorType${tmpFeature1Type}"></c:set>
															<option value="">请选择</option>
															<c:forEach items="${dictionarys[tmpOperatorTypeDicKey]}" var="dic" varStatus="status">
																<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['operatorType1']==dic.key}">selected</c:if>>${dic.value}</option>
															</c:forEach>
														</select>
														<select name="operator1" id="${tmpCondition2.conditionId}-operator1" onchange="operator1Change('${tmpCondition2.conditionId}');" style="width:120px;float:left;margin-left:4px;"  class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError" <c:if test="${tmpCondition2.conParaMap['operator1'] == null || tmpCondition2.conParaMap['operator1'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:if test="${tmpFeature1Type == 'date' && tmpOperatorType1 == '1'}">
																<c:forEach items="${dictionarys['dateComputeOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'date' && tmpOperatorType1 == '2'}">
																<c:forEach items="${dictionarys['dateCompareOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
															<c:if test="${(tmpFeature1Type == 'number'||tmpFeature1Type == 'numberSet')&& tmpOperatorType1 == '1'}">
																<c:forEach items="${dictionarys['numberComputeOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'number' && tmpOperatorType1 == '2'}">
																<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																	<c:if test="${dic.key != '%' || (dic.key == '%' && tmpFeatureClass12 == '0') }">
																		<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																	</c:if>
																</c:forEach>
																<c:if test="${tmpCondition2.conParaMap['featureType1']=='0'}">
																	<option value="in" <c:if test="${tmpCondition2.conParaMap['operator1']=='in'}">selected</c:if>>in</option>
																	<option value="notIn" <c:if test="${tmpCondition2.conParaMap['operator1']=='notIn'}">selected</c:if>>not in</option>
																	<option value="startsWith" <c:if test="${tmpCondition2.conParaMap['operator1']=='startsWith'}">selected</c:if>>startsWith</option>
																	<option value="endsWith" <c:if test="${tmpCondition2.conParaMap['operator1']=='endsWith'}">selected</c:if>>endsWith</option>
																</c:if>
															</c:if>
															<c:if test="${tmpFeature1Type == 'numberSet'&&(null ==tmpOperatorType1 || tmpOperatorType1 =='' ||tmpOperatorType1 == '2')}">
																<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																	<c:if test="${dic.key != '%' || (dic.key == '%' && tmpFeatureClass12 == '0') }">
																		<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																	</c:if>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'cnumberSet' && tmpCondition2.conParaMap['operatorType1']=='call'}">
																<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																	<c:if test="${dic.key != '%' || (dic.key == '%' && tmpFeatureClass12 == '0') }">
																		<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																	</c:if>
																</c:forEach>
															</c:if>
															<c:if test="${tmpFeature1Type == 'string' && tmpOperatorType1 == '2'}">
																<c:forEach items="${dictionarys['stringCompareOpt']}" var="dic" varStatus="status">
																	<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['operator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:forEach>
															</c:if>
														</select> 
														<select name="featureType2" id="${tmpCondition2.conditionId}-featureType2" onchange="hideSetValue('${tmpCondition2.conditionId}','5');featureType2Change('${tmpCondition2.conditionId}');" style="width:120px;float:left;margin-left:4px;"  class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError" <c:if test="${tmpCondition2.conParaMap['featureType2'] == null || tmpCondition2.conParaMap['featureType2'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['featureType']}" var="type" varStatus="status">
																<option value="${type.key }" <c:if test="${tmpFeatureClass22==type.key}">selected</c:if>>${type.value }</option>
															</c:forEach>
														</select> 
														<select name="feature2" id="${tmpCondition2.conditionId}-feature2" onchange="hideSetValue('${tmpCondition2.conditionId}','5');feature2Change('${tmpCondition2.conditionId}');" class="select-picker validate[required]" data-width="180px" data-size="10" data-dropdown-align-right="true" data-live-search="true"  data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError" <c:if test="${tmpCondition2.conParaMap['featureType2'] == null || tmpCondition2.conParaMap['featureType2'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:forEach items="${featureMapWithClassKey[tmpFeatureClass22]}" var="tmpFeature" varStatus="status">
														    	<c:if test="${(tmpFeatureClass22 == '0' && tmpFeature.value.attributeType!='string') || (tmpFeatureClass22 == '1') || (tmpFeatureClass22 == '2' && tmpFeature.value.attributeType == 'number')}">
														    		<option value="${tmpFeature.key }" <c:if test="${tmpCondition2.conParaMap['feature2']==tmpFeature.key}">selected</c:if>>${tmpFeature.value.attributeName}</option>
														    	</c:if>
														    </c:forEach>
														</select>
														<select name="numberSetOperator2" id="${tmpCondition2.conditionId}-numberSetOperator2" style="width:120px;float:left;margin-left:4px;<c:if test="${tmpFeature2Type!='numberSet' }">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['numberSetOperator']}" var="dic" varStatus="status">
																<c:if test="${dic.key!='all'&& dic.key!='incre' && dic.key!='decre'}">
																	<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['numberSetOperator1']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:if>
															</c:forEach>
														</select>
														<select name="operator2" id="${tmpCondition2.conditionId}-operator2" onchange="operator2Change('${tmpCondition2.conditionId}');" style="width:120px;float:left;margin-left:4px;"  class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError" <c:if test="${tmpCondition2.conParaMap['operator2'] == null || tmpCondition2.conParaMap['operator2'] == ''}">isHidden="true"</c:if>>
															<option value="">请选择</option>
															<c:forEach items="${dictionarys['numberCompareOpt']}" var="dic" varStatus="status">
																<c:if test="${dic.key != '%'}">
																	<option value="${dic.key}" <c:if test="${tmpCondition2.conParaMap['operator2']==dic.key}">selected</c:if>>${dic.value}</option>
																</c:if>
															</c:forEach>
														</select> 
                                                        <select name="contrastValueTypes" id="${tmpCondition2.conditionId}-contrastValueType" onchange="contrastValueTypeChange('${tmpCondition2.conditionId}');" style="width:120px;float:left;margin-left:4px;<c:if test="${tmpCondition2.conParaMap['contrastValueType']=='' || tmpCondition2.conParaMap['contrastValueType'] == null}">display:none;</c:if>" class="validate[required]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError">
															<option value="">请选择</option>
															<c:if test="${tmpCondition2.conParaMap['featureType1']=='0' && tmpFeature1Type == 'string' && (tmpOperator1 == '=='||tmpOperator1 =='!='||tmpOperator1=='in'||tmpOperator1=='notIn') &&null != businessDic[tmpFeature1ID]}">
																<option value="2" <c:if test="${tmpCondition2.conParaMap['contrastValueType']=='2'}">selected</c:if>>字典项</option>
															</c:if>
															<c:if test="${tmpFeature1Type != 'date' }">
																<option value="0" <c:if test="${tmpContrastValueType =='0'}">selected</c:if>>输入值</option>
															</c:if>
															<c:if test="${tmpCondition2.conParaMap['featureType1']=='0' && ((tmpFeature1Type == 'string' && (tmpOperator1 == '=='||tmpOperator1 =='!='))|| tmpFeature1Type == 'date') }">
																<option value="1" <c:if test="${tmpContrastValueType =='1'}">selected</c:if>>基本特征</option>
															</c:if>
															<c:if test="${tmpCondition2.conParaMap['featureType1']=='0' && null != redisSetMap[tmpFeature1ID] }">
																<option value="3" <c:if test="${tmpContrastValueType =='3'}">selected</c:if>>集合</option>
															</c:if>
														</select>
														<div id="${tmpCondition2.conditionId}-contrastValueDiv" class="contrastValueDiv" style="display:inline-block;float:left;margin-left:4px;">
															<c:if test="${null == tmpContrastValueType || tmpContrastValueType == '' || tmpContrastValueType == '0' }">
																<c:choose>
																	<c:when test="${fn:contains(tmpFeature1Type,'number') && tmpOperator1 != '%' && tmpOperator1 != 'in' && tmpOperator1 != 'notIn' && tmpOperator1 != 'startsWith' && tmpOperator1 != 'endsWith'}">
																		<input type="text" class="formElement form-control validate[required,maxSize[50],custom[positivenumber]]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError" value="${tmpCondition2.conParaMap['contrastValue'] }"
																				style="width:200px;" onchange="contrastValueChange('${tmpCondition2.conditionId}')" id="${tmpCondition2.conditionId}-contrastValue" name="contrastValue" <c:if test="${tmpCondition2.conParaMap['contrastValue'] == null || tmpCondition2.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	</c:when>
																	<c:when test="${fn:contains(tmpFeature1Type,'number') && tmpOperator1 == '%'}">
																		<input type="text" class="formElement form-control validate[required,maxSize[50],custom[positiveInteger]]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError" value="${tmpCondition2.conParaMap['contrastValue'] }"
																				style="width:200px;" onchange="contrastValueChange('${tmpCondition2.conditionId}')" id="${tmpCondition2.conditionId}-contrastValue" name="contrastValue" <c:if test="${tmpCondition2.conParaMap['contrastValue'] == null || tmpCondition2.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	</c:when>
																	<c:otherwise>
																		<input type="text" class="formElement form-control validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError" value="${tmpCondition2.conParaMap['contrastValue'] }"
																		style="width:200px;" onchange="contrastValueChange('${tmpCondition2.conditionId}')" id="${tmpCondition2.conditionId}-contrastValue" name="contrastValue" <c:if test="${tmpCondition2.conParaMap['contrastValue'] == null || tmpCondition2.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	</c:otherwise>
																</c:choose>
															</c:if>
															<c:if test="${tmpContrastValueType == '1' }"><!-- 基本特征 -->
																<select class="formElement validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError"
																        style="width:200px;" onchange="contrastValueChange('${tmpCondition2.conditionId}')" id="${tmpCondition2.conditionId}-contrastValue" name="contrastValue" >
																	<option value="">请选择</option>
																	<c:if test="${fn:contains(tmpFeature1Type,'number')}">
																		<c:forEach items="${baseFeatureNoTypeMap}" var="baseFeature" varStatus="status">
																			<c:if test="${ baseFeature.value.attributeType == 'number'}">
																				<option value="${baseFeature.key}" <c:if test="${tmpCondition2.conParaMap['contrastValue'] == baseFeature.key}">selected</c:if>>${baseFeature.value.attributeName}</option>
																			</c:if>
																		</c:forEach>
																	</c:if>
																	<c:if test="${tmpFeature1Type == 'date'}">
																		<c:forEach items="${baseFeatureNoTypeMap}" var="dateFeature" varStatus="status">
																			<c:if test="${ dateFeature.value.attributeType == 'date'}">
																				<option value="${dateFeature.key}" <c:if test="${tmpCondition2.conParaMap['contrastValue'] == dateFeature.key}">selected</c:if>>${dateFeature.value.attributeName}</option>
																			</c:if>
																		</c:forEach>
																	</c:if>
																	<c:if test="${tmpFeature1Type == 'string'}">
																		<c:forEach items="${baseFeatureNoTypeMap}" var="tmp" varStatus="status">
																				<option value="${tmp.key}" <c:if test="${tmpCondition2.conParaMap['contrastValue'] == tmp.key}">selected</c:if>>${tmp.value.attributeName}</option>
																		</c:forEach>
																	</c:if>
																</select>
															</c:if>
															<c:if test="${tmpContrastValueType == '2' }"><!-- 字典项 -->
																<c:if test="${tmpCondition2.conParaMap['featureType1']=='0' &&(tmpOperator1 == '=='||tmpOperator1 =='!='||tmpOperator1=='in'||tmpOperator1=='notIn') &&null != businessDic[tmpFeature1ID] }">
																	<select class="formElement validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError"
																	        style="width:200px;" onchange="contrastValueChange('${tmpCondition2.conditionId}')" id="${tmpCondition2.conditionId}-contrastValue" name="contrastValue" 
																	        <c:if test="${tmpOperator1 == 'in'|| tmpOperator1 == 'notIn'}"> multiple = "multiple"</c:if>
																	        <c:if test="${tmpCondition2.conParaMap['contrastValue'] == null || tmpCondition2.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																		<option value="">请选择</option>
																		<c:forEach items="${businessDic[tmpFeature1ID]}" var="dic" varStatus="status">
																			<option value="${dic.key}" <c:if test="${fn:contains(tmpCondition2.conParaMap['contrastValue'],dic.key)}">selected</c:if>>${dic.value}</option>
																		</c:forEach>
																	</select>
																</c:if>
															</c:if>
                                                            <c:if test="${tmpContrastValueType == '3' && null != redisSetMap[tmpFeature1ID]}"><!-- Redis集合 -->
																<select class="formElement validate[required,maxSize[50]]" data-prompt-position="inline" data-prompt-target="${tmpCondition2.conditionId}-conditionDivError"
																        style="width:200px;" onchange="contrastValueChange('${tmpCondition2.conditionId}')" id="${tmpCondition2.conditionId}-contrastValue" name="contrastValue" 
																        <c:if test="${tmpCondition2.conParaMap['contrastValue'] == null || tmpCondition2.conParaMap['contrastValue'] == ''}">isHidden="true"</c:if>>
																	<option value="">请选择</option>
																	<c:forEach items="${redisSetMap[tmpFeature1ID]}" var="entry" varStatus="status">
																		<option value="${entry.key}" <c:if test="${tmpCondition2.conParaMap['contrastValue'] == entry.key}">selected</c:if>>${entry.value}</option>
																	</c:forEach>
																</select>
															</c:if>
															
														</div>
									    				<div id="${tmpCondition2.conditionId}-conditionDivError" class="conditionErrorInfo"></div> 
									    			</div>
									    		</td>
									    		<td style="width:100px;">
									    			<a data-duplicate-remove href="#" class="btn icon-padding">
														<span class="img-deleteXIcon"><fmt:message>rtool_6</fmt:message></span>
													</a>
									    		</td>
									    	</tr>
									    </table>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</div><!-- ruleConditions end -->
				</div>
				<!-- 规则条件End -->
				
				<div class="button-row">
					<div style="align:center;padding-left:43%;padding-bottom:20px;">
						<button type="button" id="submitBtn"
							class="btn btn-default-submit"
							onclick="ruleEditFormSubmit();">
							<fmt:message>rtool_3</fmt:message>
						</button>
						<button type="reset" class="btn btn-default" onclick="closeRulePage();">
							<fmt:message>rtool_4</fmt:message>
						</button>
					</div>
				</div>

			</form>
		</div>
	</div>
</div>

<c:if test="${isChildPage!=1 }">
<div id="modal">
</div>
<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" aria-labelledby="msgModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content col-md-6 col-md-offset-3">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="msgModalLabel"><fmt:message>msg_1</fmt:message></h4>
      </div>
      <div class="modal-body">
        <div>
          <form class="form-horizontal" role="form">
            <div class="form-group-sm row">
              <i class="messager-icon messager-info"></i>
              <div class="col-sm-offset-2" style="padding-right:6px;">
                <p></p>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</c:if>


<script src="${skinPath}/js/common.js"></script>
<script src="${skinPath}/js/uuid.js"></script>
<script src="${skinPath}/js/ruleContent/condition.js"></script>
<script src="${skinPath}/js/ruleContent/rule.js"></script>
<script src="${pluginPath}/jquery_validationEngine/jquery.validationEngine-zh_CN.js"></script>
<script>
$(document).ready(function() {
    var isInternal = $("#isInternal").val();
    if(isInternal == '1'){
    	var minHeight = $(".leftdiv").outerHeight(true) - 50;
		$("#editRuleContentBgWhite").css("min-height", minHeight);
    }else{
    	$("#editRuleContentBgWhite").css("min-height", "100%");
    }
	$(".select-picker:not([isHidden='true'])").selectpicker('refresh');
	//隐藏值为空的控件
	$("[isHidden='true']").css("display","none");
	$("[isHidden='true']").removeAttr("isHidden");
	$("select[multiple = 'multiple'][name='contrastValue'] ").multiselect({
		buttonWidth: '200px',
		buttonContainer: '<div class="btn-group paddingTop" />',
		delimiterText: '&'
	});
	$("select[multiple = 'multiple'][name='contrastValue'] ").each(function(){
		$(this).parents(".contrastValueDiv").css("width","200px");
	});
	
	// 删除规则条件
		function removeFromArr(item, arr) {
			var idx = arr.indexOf(item);
			if (idx < 0) {
				return false;
			} else {
				arr.splice(idx, 1);//从数组中删除数据
				return true;
			}
		}
		
		$('a[data-duplicate-for=ruleCondition]').each(function() {
			var name = 'ruleCondition';
			var max = $(this).attr('data-duplicate-max');
			var origin = $('[data-duplicate-name=' + name + ']:first');
			var dummy = origin.clone();
			var items = [];
			
			$("[data-duplicate-name='ruleCondition']").each(function(){
				var it = $(this);
				items.push(it);
				var dismiss = it.find('[data-duplicate-remove]');
				dismiss.click(function() {
					removeFromArr(it, items);
					it.remove();
					updateButton();
				});
			});

			function updateButton() {
				if (items.length >= max) {
					$("#addRuleCondition").addClass('disabled');
				} else {
					$("#addRuleCondition").removeClass('disabled');
				}
			}

			function insertItem(item) {
				if (items.length >= max)
					return;
				var currentId = new UUID().id;

				item.find('[id]').each(function() {
					this.id = this.id.replace(/0-/,  currentId + '-');
				});
				item.css("display","");
				var dismiss = item.find('[data-duplicate-remove]');
				dismiss.click(function() {
					removeFromArr(item, items);
					item.remove();
					updateButton();
				});

				$(items[items.length - 1]).after(item);
				items.push(item);

				updateButton();
				currentId++;
			}
			$(this).click(function(event) {
				event.preventDefault();//阻止元素发生默认的行为
				insertItem(dummy.clone());
				return false;
			});
			insertItem(dummy.clone());
		});
		
		function editAjaxValidationCallback(status, form, json, options) {
		    var ruleType = $("#ruleType").val();
		    var url = common.path+"/ruleContent/update.do";
		   	if(status===true){
			    $.ajax({
					type : "POST",
					dataType : "json",
					url : url,
					data : $('#ruleEditForm').serialize(),
					error : common.error,
					success : function(map) {
						if(map.message!=""&map.message!=null){
							common.msg(map.message);
						}
						if(map.success){
						    var isChildPage = $("#isChildPage").val();
							if(isChildPage == '1'){
								$("#ruleContentAddPage").empty();
								$("#ruleContentAddLi").hide();
								$("#searchRule").click();
								$("#act").click();
							}else{
							    if(map.message==""||map.message==null){
									common.msg("success");
								}
							    window.opener=null;
								window.open('','_self');
								window.close();
							}
						}
					}
				});
		   	}
		};
		
		function beforeCall(form, options) {
		     return true;
		 }
 
		$('#ruleEditForm').validationEngine(
		        'attach', {
		        	scroll:true,//屏幕自动滚动到第一个验证不通过的位置
		            relative: true,
		        	showPrompts:true,//是否显示提示信息
		            promptPosition: "centerRight",
		            validateNonVisibleFields: false,
		            ajaxFormValidation: true,
		            ajaxFormValidationMethod: 'post',
		            ajaxFormValidationURL: $("#path").val() + '/validation.do',
		            maxErrorsPerField:1,
		            showOneMessage:true,
		            onAjaxFormComplete: editAjaxValidationCallback,
		            onBeforeAjaxFormValidation: beforeCall
		        }
		);
	
});

function closeRulePage(){
	var isChildPage = $("#isChildPage").val();
	if(isChildPage == '1'){
		$("#ruleContentAddPage").empty();
		$("#ruleContentAddLi").hide();
		$("#act").click();
	}else{
	    window.opener=null;
		window.open('','_self');
		window.close();
	}
}

function ruleEditFormSubmit(){
	$("#ruleEditForm").submit();
}
</script>
