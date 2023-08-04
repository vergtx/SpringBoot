package edu.pnu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.BalanceSheet;
import edu.pnu.domain.CashFlowStatement;
import edu.pnu.domain.CodeList;
import edu.pnu.domain.Company;
import edu.pnu.domain.IncomeStatement;

import edu.pnu.persistence.BalanceSheetRepository;
import edu.pnu.persistence.CashFlowStatementRepository;
import edu.pnu.persistence.IncomeStatementRepository;
import edu.pnu.service.CodeListService;
import edu.pnu.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

	private final CompanyService companyService;
	private final CodeListService codeListService;
	private final BalanceSheetRepository balanceSheetRepository;
	private final CashFlowStatementRepository cashFlowStatementRepository;
	private final IncomeStatementRepository incomeStatementRepository;

	@Autowired
	public CompanyController(CompanyService companyService, CodeListService codeListService,
			BalanceSheetRepository balanceSheetRepository, CashFlowStatementRepository cashFlowStatementRepository,
			IncomeStatementRepository incomeStatementRepository) {
		this.companyService = companyService;
		this.codeListService = codeListService;
		this.balanceSheetRepository = balanceSheetRepository;
		this.cashFlowStatementRepository = cashFlowStatementRepository;
		this.incomeStatementRepository = incomeStatementRepository;
	}

	@GetMapping
	public List<Company> getAllCompany() {
		return companyService.getAllCompany();

	}
//
//    @GetMapping("/{companyName}")
//    public String getStockCodeByCompanyName(@PathVariable String companyName) {
//        String stockCode = companyService.getStockCodeByCompanyName(companyName);
//        if (stockCode != null) {
//            return stockCode;
//        } else {
//            return "회사 이름이 잘못되었습니다!";
//        }
//    }

	@GetMapping("/{stockCode}")
	public Company getCompanyByStockCode(@PathVariable String stockCode) {
		return companyService.getCompanyByStockCode(stockCode);

	}

	@GetMapping("/{FinancialStatement}/{stockCode}")
	public ResponseEntity<Map<String, Object>> getFinancialStatementByStockCode(
	        @PathVariable String FinancialStatement,
	        @PathVariable String stockCode
	) {
	    // 재무상태표 종류에 따라 해당하는 데이터를 조회
	    // B: 재무상태표, C: 현금흐름표, I: 손익계산서
	    String statementType;
	    if (FinancialStatement.equalsIgnoreCase("B")) {
	        statementType = "B";
	    } else if (FinancialStatement.equalsIgnoreCase("C")) {
	        statementType = "C";
	    } else if (FinancialStatement.equalsIgnoreCase("I")) {
	        statementType = "I";
	    } else {
	        return ResponseEntity.badRequest().build();
	    }

	    // 해당 재무상태표 종류에 따른 codelist 테이블의 item_code 내용 출력 
	    Map<String, Object> resultMap = new HashMap<>();
	    List<Map<String, Object>> statements = new ArrayList<>();
	    for (CodeList codeList : codeListService.getFinancialStatementItems(statementType, stockCode)) {
	        Map<String, Object> statementMap = new HashMap<>();
	        statementMap.put("itemCode", codeList.getItemCode());
	        statementMap.put("itemName", codeList.getItemName());

	        if (FinancialStatement.equalsIgnoreCase("B")) {
	            BalanceSheet balanceSheet = balanceSheetRepository.findByStockCodeAndItemCode(stockCode, codeList.getItemCode());
	            if (balanceSheet != null) {
	                statementMap.put("y2020", balanceSheet.getY2020());
	                statementMap.put("y2021", balanceSheet.getY2021());
	                statementMap.put("y2022", balanceSheet.getY2022());
	            }
	        } else if (FinancialStatement.equalsIgnoreCase("C")) {
	            CashFlowStatement cashFlowStatement = cashFlowStatementRepository.findByStockCodeAndItemCode(stockCode, codeList.getItemCode());
	            if (cashFlowStatement != null) {
	                statementMap.put("y2020", cashFlowStatement.getY2020());
	                statementMap.put("y2021", cashFlowStatement.getY2021());
	                statementMap.put("y2022", cashFlowStatement.getY2022());
	            }
	        } else if (FinancialStatement.equalsIgnoreCase("I")) {
	            IncomeStatement incomeStatement = incomeStatementRepository.findByStockCodeAndItemCode(stockCode, codeList.getItemCode());
	            if (incomeStatement != null) {
	                statementMap.put("y2020", incomeStatement.getY2020());
	                statementMap.put("y2021", incomeStatement.getY2021());
	                statementMap.put("y2022", incomeStatement.getY2022());
	            }
	        }

	        statements.add(statementMap);
	    }

	    resultMap.put("statements", statements);

	    return ResponseEntity.ok(resultMap);
	}
	
	




}
