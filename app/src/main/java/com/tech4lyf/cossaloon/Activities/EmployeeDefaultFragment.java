package com.tech4lyf.cossaloon.Activities;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Bill;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Service;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterBillItems;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeDefaultFragment extends Fragment implements View.OnClickListener, Listeners.OnClickBillItemListListener {
    private Integer billTotalPrice1, billTotalPrice2, billTotalPrice3, billTotalPrice4, billTotalPrice5, billTotalPrice6, billTotalPrice7, billTotalPrice8, billTotalPrice9, billTotalPrice10, billTotalPrice11, billTotalPrice12 = 0;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterBillItems recyclerViewAdapterBillItems;
    private ArrayList<Service> serviceList = new ArrayList<>();
    private CardView enterBill;
    private View root;
    private DatabaseReference databaseReferenceServices;
    private DatabaseReference databaseReferenceBills;
    private DatabaseReference databaseReferenceIncomes;
    private DatabaseReference databaseReferenceJobsToday;
    private DatabaseReference databaseReferenceJobsThisMonth;
    private Employee employee;
    private String currentDate;
    private String currentMonth;
    private String currentYear;
    private String fullTime;
    private String fullDate;
    private ArrayList<String> serviceListItemNames = new ArrayList<>();
    private ArrayList<Integer> serviceListItemPrices = new ArrayList<>();
    private Integer totalDailyEmployee = 0;
    private Integer totalDailyStore = 0;
    private Integer totalDailyArea = 0;
    private Integer totalDailyCombined = 0;
    private Integer totalMonthlyEmployee = 0;
    private Integer totalMonthlyStore = 0;
    private Integer totalMonthlyArea = 0;
    private Integer totalMonthlyCombined = 0;
    private Integer totalYearlyEmployee = 0;
    private Integer totalYearlyStore = 0;
    private Integer totalYearlyArea = 0;
    private Integer totalYearlyCombined = 0;
    private Integer receivedTotalDailyEmployee = 0;
    private Integer receivedTotalDailyStore = 0;
    private Integer receivedTotalDailyArea = 0;
    private Integer receivedTotalDailyCombined = 0;
    private Integer receivedTotalMonthlyEmployee = 0;
    private Integer receivedTotalMonthlyStore = 0;
    private Integer receivedTotalMonthlyArea = 0;
    private Integer receivedTotalMonthlyCombined = 0;
    private Integer receivedTotalYearlyEmployee = 0;
    private Integer receivedTotalYearlyStore = 0;
    private Integer receivedTotalYearlyArea = 0;
    private Integer receivedTotalYearlyCombined = 0;
    private Integer addToCount1,addToCount2,addToCount3,addToCount4,addToCount5,addToCount6,addToCount7,addToCount8 = 0;

    public EmployeeDefaultFragment() {
        // Required empty public constructor
    }

    public EmployeeDefaultFragment(Employee employee) {
        this.employee = employee;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_employee_default, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = root.findViewById(R.id.employee_default_put_bill_recycler_view);
        enterBill = root.findViewById(R.id.employee_dashBoard_default_enter_bill);

        currentDate = FormatData.getCurrentDeviceDate();
        currentMonth = FormatData.getCurrentDeviceMonth();
        currentYear = FormatData.getCurrentDeviceYear();
        fullDate = FormatData.getCurrentDeviceFullDate();
        fullTime = FormatData.getCurrentDeviceFullTime();


        recyclerViewAdapterBillItems = new RecyclerViewAdapterBillItems(serviceList);
        recyclerView.setAdapter(recyclerViewAdapterBillItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewAdapterBillItems.notifyDataSetChanged();
        databaseReferenceServices = FirebaseDatabase.getInstance().getReference().child("Services");
        databaseReferenceBills = FirebaseDatabase.getInstance().getReference().child("Bills");
        databaseReferenceIncomes = FirebaseDatabase.getInstance().getReference().child("Incomes");
        databaseReferenceJobsToday = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Today" + currentDate);
        databaseReferenceJobsThisMonth = FirebaseDatabase.getInstance().getReference().child("Jobs").child("ThisMonth" + currentMonth);


        Listeners.setOnClickBillItemListListeners(this);


        fireBaseListener();


        enterBill.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.employee_dashBoard_default_enter_bill:
                enterBill();
                break;
            default:
                break;

        }
    }

    void enterBill() {

        currentDate = FormatData.getCurrentDeviceDate();
        currentMonth = FormatData.getCurrentDeviceMonth();
        currentYear = FormatData.getCurrentDeviceYear();
        fullDate = FormatData.getCurrentDeviceFullDate();
        fullTime = FormatData.getCurrentDeviceFullTime();


        String key = databaseReferenceBills.push().getKey();
        final Bill bill = new Bill(key, employee.getAreaId(), employee.getAreaName(), employee.getStoreId(), employee.getStoreName(),
                employee.getId(), employee.getName(), fullTime, fullDate, serviceListItemNames, serviceListItemPrices);

        billTotalPrice1 = billTotalPrice2 = billTotalPrice3 = billTotalPrice4 = billTotalPrice5 = billTotalPrice6 = billTotalPrice7
                = billTotalPrice8 = billTotalPrice9 = billTotalPrice10 = billTotalPrice11 = billTotalPrice12 = bill.getTotalPrice();


        addToCount1 =addToCount2 = 1;


        databaseReferenceBills.child(currentYear).child(currentMonth).child(currentDate).child(key).setValue(bill)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Bill Entered Successfully!", Toast.LENGTH_SHORT).show();
                        {
                            databaseReferenceJobsToday.child(employee.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Integer receivedCount;
                                    if (dataSnapshot.exists()) {
                                        receivedCount = dataSnapshot.getValue(Integer.class);
                                    } else
                                        receivedCount = 0;
                                    databaseReferenceJobsToday.child(employee.getId()).setValue(receivedCount + addToCount1);
                                    addToCount1 = 0;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            databaseReferenceJobsToday.child(employee.getStoreId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Integer receivedCount;
                                    if (dataSnapshot.exists()) {
                                        receivedCount = dataSnapshot.getValue(Integer.class);
                                    } else
                                        receivedCount = 0;
                                    databaseReferenceJobsToday.child(employee.getStoreId()).setValue(receivedCount + addToCount2);
                                    addToCount2 = 0;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            databaseReferenceJobsToday.child(employee.getAreaId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Integer receivedCount;
                                    if (dataSnapshot.exists()) {
                                        receivedCount = dataSnapshot.getValue(Integer.class);
                                    } else
                                        receivedCount = 0;
                                    databaseReferenceJobsToday.child(employee.getAreaId()).setValue(receivedCount + addToCount3);
                                    addToCount3=0;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            databaseReferenceJobsToday.child("Combined").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Integer receivedCount;
                                    if (dataSnapshot.exists()) {
                                        receivedCount = dataSnapshot.getValue(Integer.class);
                                    } else
                                        receivedCount = 0;
                                    databaseReferenceJobsToday.child("Combined").setValue(receivedCount + addToCount4);
                                    addToCount4 =0;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            databaseReferenceJobsThisMonth.child(employee.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Integer receivedCount;
                                    if (dataSnapshot.exists()) {
                                        receivedCount = dataSnapshot.getValue(Integer.class);
                                    } else
                                        receivedCount = 0;
                                    databaseReferenceJobsThisMonth.child(employee.getId()).setValue(receivedCount + addToCount5);
                                    addToCount5 =0;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            databaseReferenceJobsThisMonth.child(employee.getStoreId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Integer receivedCount;
                                    if (dataSnapshot.exists()) {
                                        receivedCount = dataSnapshot.getValue(Integer.class);
                                    } else
                                        receivedCount = 0;
                                    databaseReferenceJobsThisMonth.child(employee.getStoreId()).setValue(receivedCount + addToCount6);
                                    addToCount6=0;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            databaseReferenceJobsThisMonth.child(employee.getAreaId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Integer receivedCount;
                                    if (dataSnapshot.exists()) {
                                        receivedCount = dataSnapshot.getValue(Integer.class);
                                    } else
                                        receivedCount = 0;
                                    databaseReferenceJobsThisMonth.child(employee.getAreaId()).setValue(receivedCount + addToCount7);
                                    addToCount7 =0;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            databaseReferenceJobsThisMonth.child("Combined").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Integer receivedCount;
                                    if (dataSnapshot.exists()) {
                                        receivedCount = dataSnapshot.getValue(Integer.class);
                                    } else
                                        receivedCount = 0;
                                    databaseReferenceJobsThisMonth.child("Combined").setValue(receivedCount + addToCount8);
                                    addToCount8=0;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }


                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate).child(employee.getId())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            receivedTotalDailyEmployee = dataSnapshot.getValue(Integer.class);
                                        } else
                                            receivedTotalDailyEmployee = 0;

                                        totalDailyEmployee = receivedTotalDailyEmployee + billTotalPrice1;
                                        billTotalPrice1 = 0;
                                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate)
                                                .child(employee.getId()).setValue(totalDailyEmployee);


                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate).child(employee.getStoreId())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            receivedTotalDailyStore = dataSnapshot.getValue(Integer.class);
                                        } else
                                            receivedTotalDailyStore = 0;

                                        totalDailyStore = receivedTotalDailyStore + billTotalPrice2;
                                        billTotalPrice2 = 0;
                                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate).child(employee.getStoreId()).setValue(totalDailyStore);


                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate).child(employee.getAreaId())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            receivedTotalDailyArea = dataSnapshot.getValue(Integer.class);
                                        } else
                                            receivedTotalDailyArea = 0;

                                        totalDailyArea = receivedTotalDailyArea + billTotalPrice3;
                                        billTotalPrice3 = 0;
                                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate).child(employee.getAreaId()).setValue(totalDailyArea);


                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate).child("Combined")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            receivedTotalDailyCombined = dataSnapshot.getValue(Integer.class);
                                        } else
                                            receivedTotalDailyCombined = 0;

                                        totalDailyCombined = receivedTotalDailyCombined + billTotalPrice4;
                                        billTotalPrice4 = 0;
                                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate).child("Combined").setValue(totalDailyCombined);


                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(employee.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    receivedTotalMonthlyEmployee = dataSnapshot.getValue(Integer.class);

                                } else
                                    receivedTotalMonthlyEmployee = 0;
                                totalMonthlyEmployee = receivedTotalMonthlyEmployee + billTotalPrice5;
                                billTotalPrice5 = 0;
                                databaseReferenceIncomes.child(currentYear).child(currentMonth).child(employee.getId()).setValue(totalMonthlyEmployee);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(employee.getStoreId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    receivedTotalMonthlyStore = dataSnapshot.getValue(Integer.class);

                                } else
                                    receivedTotalMonthlyStore = 0;
                                totalMonthlyStore = receivedTotalMonthlyStore + billTotalPrice6;
                                billTotalPrice6 = 0;
                                databaseReferenceIncomes.child(currentYear).child(currentMonth).child(employee.getStoreId()).setValue(totalMonthlyStore);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(employee.getAreaId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    receivedTotalMonthlyArea = dataSnapshot.getValue(Integer.class);

                                } else
                                    receivedTotalMonthlyArea = 0;
                                totalMonthlyArea = receivedTotalMonthlyArea + billTotalPrice7;
                                billTotalPrice7 = 0;
                                databaseReferenceIncomes.child(currentYear).child(currentMonth).child(employee.getAreaId()).setValue(totalMonthlyArea);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        databaseReferenceIncomes.child(currentYear).child(currentMonth).child("Combined").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    receivedTotalMonthlyCombined = dataSnapshot.getValue(Integer.class);

                                } else
                                    receivedTotalMonthlyCombined = 0;
                                totalMonthlyCombined = receivedTotalMonthlyCombined + billTotalPrice8;
                                billTotalPrice8 = 0;
                                databaseReferenceIncomes.child(currentYear).child(currentMonth).child("Combined").setValue(totalMonthlyCombined);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        databaseReferenceIncomes.child(currentYear).child(employee.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    receivedTotalYearlyEmployee = dataSnapshot.getValue(Integer.class);

                                } else
                                    receivedTotalYearlyEmployee = 0;
                                totalYearlyEmployee = receivedTotalYearlyEmployee + billTotalPrice9;
                                billTotalPrice9 = 0;
                                databaseReferenceIncomes.child(currentYear).child(employee.getId()).setValue(totalYearlyEmployee);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        databaseReferenceIncomes.child(currentYear).child(employee.getStoreId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    receivedTotalYearlyStore = dataSnapshot.getValue(Integer.class);

                                } else
                                    receivedTotalYearlyStore = 0;
                                totalYearlyStore = receivedTotalYearlyStore + billTotalPrice10;
                                billTotalPrice10 = 0;
                                databaseReferenceIncomes.child(currentYear).child(employee.getStoreId()).setValue(totalYearlyStore);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        databaseReferenceIncomes.child(currentYear).child(employee.getAreaId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    receivedTotalYearlyArea = dataSnapshot.getValue(Integer.class);

                                } else
                                    receivedTotalYearlyArea = 0;
                                totalYearlyArea = receivedTotalYearlyArea + billTotalPrice11;
                                billTotalPrice11 = 0;
                                databaseReferenceIncomes.child(currentYear).child(employee.getAreaId()).setValue(totalYearlyArea);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        databaseReferenceIncomes.child(currentYear).child("Combined").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    receivedTotalYearlyCombined = dataSnapshot.getValue(Integer.class);

                                } else
                                    receivedTotalYearlyCombined = 0;
                                totalYearlyCombined = receivedTotalYearlyCombined + billTotalPrice12;
                                billTotalPrice12 = 0;
                                databaseReferenceIncomes.child(currentYear).child("Combined").setValue(totalYearlyCombined);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Action Failed", Toast.LENGTH_SHORT).show();


            }
        });


        serviceListItemPrices.clear();
        serviceListItemNames.clear();
        recyclerViewAdapterBillItems = new RecyclerViewAdapterBillItems(serviceList);
        recyclerView.setAdapter(recyclerViewAdapterBillItems);


    }

    private void fireBaseListener() {
        // String key = databaseReferenceServices.push().getKey();

        //   databaseReferenceServices.child(key).setValue(new Service(key, "Shaving", 40));


        databaseReferenceServices.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Service service = dataSnapshot.getValue(Service.class);
                    if (service != null) {
                        synchronized (serviceList) {
                            if (!serviceList.contains(service)) {
                                serviceList.add(service);
                                recyclerViewAdapterBillItems.setServiceList(serviceList);
                                recyclerViewAdapterBillItems.notifyDataSetChanged();
                            }


                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Service service = dataSnapshot.getValue(Service.class);
                    if (service != null) {
                        synchronized (serviceList) {
                            serviceList.remove(service);
                            recyclerViewAdapterBillItems.setServiceList(serviceList);
                            recyclerViewAdapterBillItems.notifyDataSetChanged();

                        }
                    }

                }
            }


            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onClick(Service service, Context.FLAG flag) {

        if (flag == Context.FLAG.ADD) {
            serviceListItemNames.add(service.getName());
            serviceListItemPrices.add(service.getPrice());
        } else if (flag == Context.FLAG.DELETE) {
            serviceListItemNames.remove(service.getName());
            serviceListItemPrices.remove(service.getPrice());
        }


    }
}
