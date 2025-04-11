/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:40 AM - 17/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.config;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SignatureAlgorithmConfig {
	@Getter
	private List<SignatureAlgorithmConfig.Algorithm> algorithmSignature;
	private static SignatureAlgorithmConfig instance;

	public record Algorithm(String alg, List<String> hashAlgorithms, List<String> ins) {
	}

	private SignatureAlgorithmConfig() {
		this.init();
		algorithmSignature.sort(Comparator.comparing(algorithm -> algorithm.alg));
	}

	public static SignatureAlgorithmConfig getInstance() {
		if (instance == null)
			instance = new SignatureAlgorithmConfig();
		return instance;
	}

	private void init() {
		algorithmSignature = new ArrayList<>() {
			{
				add(new Algorithm("DSA", new ArrayList<>() {
					{
						add("SHA1");
						add("SHA224");
						add("SHA256");
						add("SHA384");
						add("SHA512");
						add("SHA3-224");
						add("SHA3-256");
						add("SHA3-384");
						add("SHA3-512");
					}
				}, new ArrayList<>() {
					{
						add("");
						add("P1363Format");
					}
				}));

				add(new Algorithm("RSA", new ArrayList<>() {
					{
						add("MD2");
						add("MD5");
						add("SHA1");
						add("SHA224");
						add("SHA256");
						add("SHA384");
						add("SHA512");
						add("SHA512/224");
						add("SHA512/256");
						add("SHA3-224");
						add("SHA3-256");
						add("SHA3-384");
						add("SHA3-512");
					}
				}, new ArrayList<>()));

				add(new Algorithm("ECDSA", new ArrayList<>() {
					{
						add("SHA1");
						add("SHA224");
						add("SHA256");
						add("SHA384");
						add("SHA512");
						add("SHA512/224");
						add("SHA512/256");
						add("SHA3-224");
						add("SHA3-256");
						add("SHA3-384");
						add("SHA3-512");
					}
				}, new ArrayList<>()));
			}
		};
	}
}
